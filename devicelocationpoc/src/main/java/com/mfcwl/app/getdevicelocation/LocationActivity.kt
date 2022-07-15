package com.mfcwl.app.getdevicelocation

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.content.*
import android.location.Location
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import android.R.attr.name
import com.google.android.material.snackbar.Snackbar
import android.R.attr.name
import android.net.Uri
import android.provider.Settings


class LocationActivity : AppCompatActivity() {

    private lateinit var mForegroundLocationService: ForegroundLocationService;
    private var mBound: Boolean = false

    private lateinit var myReceiver: MyReceiver

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    private lateinit var mLocationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myReceiver = MyReceiver()
        setContentView(R.layout.activity_location)

        findViewById<TextView>(R.id.textView).setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (mBound)
                    mForegroundLocationService.requestLocationUpdates()
            }
        })
        if (checkRuntimePermissions()) {
            deviceLocationSettings()
        } else
            requestRuntimePermissions()
    }

    private fun checkRuntimePermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestRuntimePermissions() {
        val shouldProvideRationale: Boolean = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val shouldProvideRationalee: Boolean = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (shouldProvideRationale || shouldProvideRationalee) {

            Snackbar.make(
                findViewById(R.id.activity_location),
                "Location permission is needed for core functionality",
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(android.R.string.ok, View.OnClickListener { // Request permission
                    ActivityCompat.requestPermissions(
                        this, arrayOf<String>(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ), REQUEST_PERMISSIONS_REQUEST_CODE
                    )
                })
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
//                operation cancelled

            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                deviceLocationSettings()
            } else {
                Snackbar.make(
                    findViewById(R.id.activity_location),
                    "Permission was denied, but is needed for core functionality.",
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(
                        "Settings",
                        View.OnClickListener { // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri: Uri = Uri.fromParts(
                                "package",
                                BuildConfig.APPLICATION_ID, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        })
                    .show()
            }
        }

    }

    fun deviceLocationSettings() {
        createLocationRequest()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)


        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener { locationSettingsResponse ->
            if (mBound)
                mForegroundLocationService.requestLocationUpdates()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution).build()
                    resolutionForResult.launch(intentSenderRequest)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private val resolutionForResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                Log.e("debug", "Location Settings Accepted")
                if (mBound)
                    mForegroundLocationService.requestLocationUpdates()
            } else {
                Log.e("debug", "Location Settings Not Accepted")
            }
        }

    fun createLocationRequest() {
        mLocationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, ForegroundLocationService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            myReceiver,
            IntentFilter("broadcast")
        )
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as ForegroundLocationService.LocalBinder
            mForegroundLocationService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            mBound = false
        }

    }

    class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val location: Location? =
                intent.getParcelableExtra("location")
            if (location != null) {

            }
        }
    }
}
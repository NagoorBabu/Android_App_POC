package com.mfcwl.app.getdevicelocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    private lateinit var mFusedLocationClient: FusedLocationProviderClient;
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mLocationRequest: LocationRequest

    private var mLatitude = 0.0
    private var mLongitude = 0.0

    private var mFetchLocation: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Update UI with location data
                    // ...
                    mLatitude = location.latitude
                    mLongitude = location.longitude
                    Log.e("debug", "" + "Lat : " + mLatitude + " Long : " + mLongitude)
                }
            }
        }

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

            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                deviceLocationSettings()
            } else {

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
            startLocationUpdates()
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
                startLocationUpdates()
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

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        Log.e("debug", "Location Request Started")
        mFetchLocation = true
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback, Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    override fun onPause() {
        super.onPause()
        if (mFetchLocation)
            stopLocationUpdates()
    }


}
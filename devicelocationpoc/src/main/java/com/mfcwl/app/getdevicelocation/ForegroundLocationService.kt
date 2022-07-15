package com.mfcwl.app.getdevicelocation

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*


class ForegroundLocationService : Service() {
    private val mBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): ForegroundLocationService = this@ForegroundLocationService
    }

    private lateinit var mFusedLocationClient: FusedLocationProviderClient;
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mLocationRequest: LocationRequest
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    private lateinit var mNotificationManager: NotificationManager
    private val CHANNEL_NAME = "GENERAL"
    private val CHANNEL_DESCRIPTION = "General notifications"
    private val CHANNEL_ID = "general_01"
    private val NOTIFICATION_ID = 12345678
    private val EXTRA_STARTED_FROM_NOTIFICATION: String = "started_from_notification"

    private val ACTION_BROADCAST: String = "broadcast"
    private val EXTRA_LOCATION: String = "location"


    override fun onCreate() {
        Log.i("debug", "onCreate")
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {

                    Log.e(
                        "debug",
                        "" + "Lat : " + location.latitude + " Long : " + location.longitude
                    )
                    onNewLocation(location)
                }
                removeLocationUpdates()
            }
        }
        createLocationRequest()

        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            mNotificationManager.createNotificationChannel(channel)
        }
    }

    private fun getNotification(): Notification? {
        val intent = Intent(this, ForegroundLocationService::class.java)

        val text: CharSequence = "In Progress"

        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.

        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true)

        // The PendingIntent that leads to a call to onStartCommand() in this service.

        // The PendingIntent that leads to a call to onStartCommand() in this service.
        val servicePendingIntent = PendingIntent.getService(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // The PendingIntent to launch activity.

        // The PendingIntent to launch activity.
        val activityPendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, LocationActivity::class.java), 0
        )

        /*Notice that the NotificationCompat.Builder constructor requires that you provide a channel ID.
        This is required for compatibility with Android 8.0 (API level 26) and higher, but is ignored by older versions.*/
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .addAction(
                android.R.drawable.btn_dropdown, "Open",
                activityPendingIntent
            )
            .addAction(
                android.R.drawable.ic_menu_close_clear_cancel, "Cancel",
                servicePendingIntent
            )
            .setContentText(text)
            .setContentTitle("Fetching Location")
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker(text)
            .setWhen(System.currentTimeMillis())

        // Set the Channel ID for Android O.

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID) // Channel ID
        }

        return builder.build()
    }

    fun createLocationRequest() {
        mLocationRequest = LocationRequest.create().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            numUpdates = 1
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i("debug", "onStartCommand")
        val startedFromNotification = intent.getBooleanExtra(
            EXTRA_STARTED_FROM_NOTIFICATION,
            false
        )

        // We got here because the user decided to remove location updates from the notification.

        // We got here because the user decided to remove location updates from the notification.
        if (startedFromNotification) {
            removeLocationUpdates()
            stopSelf()
        }
        // Tells the system to not try to recreate the service after it has been killed.
        // Tells the system to not try to recreate the service after it has been killed.
        return START_NOT_STICKY
    }

    fun removeLocationUpdates() {
        Log.i("debug", "Removing location updates")
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
//            stopSelf()
        } catch (unlikely: SecurityException) {
            Log.e("debug", "Lost location permission. Could not remove updates. $unlikely")
        }
    }

    private var mChangingConfiguration = false

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.i("debug", "Removing location updates")
        super.onConfigurationChanged(newConfig)
        mChangingConfiguration = true;
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i("debug", "onBind")
        // Called when a client (MainActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        stopForeground(true);
        mChangingConfiguration = false;
        return mBinder
    }

    override fun onRebind(intent: Intent) {
        Log.i("debug", "onRebind")
        // Called when a client (MainActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        stopForeground(true);
        mChangingConfiguration = false;
        super.onRebind(intent);
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("debug", "onUnbind")
        // Called when the last client (MainActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in MainActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration) {

            startForeground(NOTIFICATION_ID, getNotification());
        }
        return true; // Ensures onRebind() is called when a client re-binds.
    }

    fun requestLocationUpdates() {
        Log.i("debug", "Requesting location updates")
        startService(Intent(applicationContext, ForegroundLocationService::class.java))
        try {
            Looper.myLooper()?.let {
                Log.i("debug", "Requesting location updates inside")
                mFusedLocationClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback, it
                )
            }
        } catch (unlikely: SecurityException) {
            Log.e("debug", "Lost location permission. Could not request updates. $unlikely")
        }
    }

    private fun onNewLocation(location: Location) {
        Log.i("debug", "New location: $location")

        // Notify anyone listening for broadcasts about the new location.

        // Notify anyone listening for broadcasts about the new location.
        val intent: Intent = Intent(ACTION_BROADCAST)
        intent.putExtra(EXTRA_LOCATION, location)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

        // Update notification content if running as a foreground service.

        // Update notification content if running as a foreground service.
        if (serviceIsRunningInForeground(this)) {
            mNotificationManager.notify(NOTIFICATION_ID, getNotification())
        }
    }

    fun startFore(){

    }

    fun serviceIsRunningInForeground(context: Context): Boolean {
        val manager = context.getSystemService(
            ACTIVITY_SERVICE
        ) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (javaClass.name == service.service.className) {
                if (service.foreground) {
                    return true
                }
            }
        }
        return false
    }
}
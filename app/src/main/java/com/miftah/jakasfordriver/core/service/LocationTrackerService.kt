package com.miftah.jakasfordriver.core.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.miftah.jakasfordriver.R
import com.miftah.jakasfordriver.core.data.remote.response.GeoGamma
import com.miftah.jakasfordriver.core.data.remote.socket.SocketHandlerService
import com.miftah.jakasfordriver.ui.maps.MapsActivity
import com.miftah.jakasfordriver.utils.Constants
import com.miftah.jakasfordriver.utils.Constants.ACTION_START_SERVICE
import com.miftah.jakasfordriver.utils.Constants.ACTION_STOP_SERVICE
import com.miftah.jakasfordriver.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.miftah.jakasfordriver.utils.Constants.NOTIFICATION_ID
import com.miftah.jakasfordriver.utils.MapsUtility
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LocationTrackerService : LifecycleService() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var socketHandlerService: SocketHandlerService

    companion object {
        val angkotPosition = MutableLiveData<LatLng>()
        val userPosition = MutableLiveData<List<LatLng>>()
        val isTracking = MutableLiveData<Boolean>()
    }

    override fun onCreate() {
        super.onCreate()
        postInitialValues()

        isTracking.observe(this) {
            updateLocationTracking(it)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_SERVICE -> {
                    postInitialValues()
                    startForegroundService()
                    Timber.d("Start Service")
                }

                ACTION_STOP_SERVICE -> {
                    Timber.d("Stop Service")
                    killService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun killService() {
        isTracking.postValue(false)
        socketHandlerService.closeConnection()
        stopForeground(true)
        stopSelf()
    }

    private fun postInitialValues() {
        userPosition.postValue(mutableListOf())
        isTracking.postValue(true)
        angkotPosition.postValue(LatLng(0.0, 0.0))
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if (!MapsUtility.hasLocationPermissions(this)) return
        if (isTracking) {
            val request = LocationRequest.create().apply {
                interval = Constants.LOCATION_UPDATE_INTERVAL
                fastestInterval = Constants.FASTEST_LOCATION_INTERVAL
                priority = Priority.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationProviderClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.let { locations ->
                for (location in locations) {
                    val lastLatLng = LatLng(
                        location.latitude,
                        location.longitude
                    )
                    angkotPosition.postValue(lastLatLng)
                    socketHandlerService.sendDriverPosition(lastLatLng)
                    socketHandlerService.getPassengerPosition {
                        Timber.d("what")
                    }
                    Timber.d("NEW LOCATION: ${location.latitude}, ${location.longitude}")
                }
            }
        }
    }

    private fun startForegroundService() {
        socketHandlerService.establishConnection()
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.iconlocation2)
            .setContentTitle("Find Passenger")
            .setContentIntent(pendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getAllAngkotDirection() {
        socketHandlerService.getPassengerPosition { jsonDataList ->
            jsonDataList.forEach { jsonData ->
                val geoJson = Gson().fromJson(jsonData, GeoGamma::class.java)
            }
        }
    }

    private fun pendingIntent(): PendingIntent {
        val resultIntent = Intent(this, MapsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        return PendingIntent.getActivity(
            this,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}
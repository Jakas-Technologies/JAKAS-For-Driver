package com.miftah.jakasfordriver.ui.maps

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.miftah.jakasfordriver.R
import com.miftah.jakasfordriver.core.service.LocationTrackerService
import com.miftah.jakasfordriver.databinding.ActivityMapsBinding
import com.miftah.jakasfordriver.utils.Constants.ACTION_START_SERVICE
import com.miftah.jakasfordriver.utils.Constants.ACTION_STOP_SERVICE
import com.miftah.jakasfordriver.utils.Constants.MAP_ZOOM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var serviceRunning = false

    private var markerPosition: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnShareLocation.setOnClickListener {
            sendCommandToService(ACTION_START_SERVICE)
        }

        binding.btnStopShareLocation.setOnClickListener {
            sendCommandToService(ACTION_STOP_SERVICE)
        }
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        LocationTrackerService.isTracking.observe(this) {
            serviceIsTracking(it)
        }
        LocationTrackerService.angkotPosition.observe(this) { data ->
            if (serviceRunning) {
                markerPosition?.remove()
                markerPosition = mMap.addMarker(MarkerOptions().position(data))
                val camera = CameraPosition.builder()
                    .target(data)
                    .zoom(MAP_ZOOM)
                    .build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera), 100, null)
            }
        }
        LocationTrackerService.userPosition.observe(this) {

        }
    }

    private fun serviceIsTracking(isTracking: Boolean) {
        this.serviceRunning = isTracking
        if (isTracking) {
            binding.btnShareLocation.visibility = View.GONE
            binding.btnStopShareLocation.visibility = View.VISIBLE
        } else {
            binding.btnShareLocation.visibility = View.VISIBLE
            binding.btnStopShareLocation.visibility = View.GONE
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }


    private fun sendCommandToService(action: String) {
        Intent(this, LocationTrackerService::class.java).let {
            it.action = action
            startService(it)
        }
    }
}
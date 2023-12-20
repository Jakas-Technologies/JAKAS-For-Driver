package com.miftah.jakasfordriver.ui.maps

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.miftah.jakasfordriver.core.service.LocationTrackerService
import com.miftah.jakasfordriver.databinding.FragmentMapMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapMainFragment : Fragment() {

    private var _binding: FragmentMapMainBinding? = null
    private val binding get() = _binding!!

    private var map: GoogleMap? = null

    private var serviceRunning = false
    private var markerPosition: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync {
            map = it
        }
    }

    private fun subscribeToObservers() {
        LocationTrackerService.isTracking.observe(this) {
//            serviceIsTracking(it)
        }
        LocationTrackerService.angkotPosition.observe(this) { data ->
            /*if (serviceRunning) {
                markerPosition?.remove()
                markerPosition = mMap.addMarker(MarkerOptions().position(data))
                val camera = CameraPosition.builder()
                    .target(data)
                    .zoom(MAP_ZOOM)
                    .build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera), 100, null)
            }*/
        }
        LocationTrackerService.userPosition.observe(this) {

        }
    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), LocationTrackerService::class.java).let {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}
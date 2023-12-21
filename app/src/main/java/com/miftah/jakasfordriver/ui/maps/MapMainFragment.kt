package com.miftah.jakasfordriver.ui.maps

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.miftah.jakasfordriver.R
import com.miftah.jakasfordriver.core.service.LocationTrackerService
import com.miftah.jakasfordriver.databinding.FragmentMapMainBinding
import com.miftah.jakasfordriver.utils.Constants
import com.miftah.jakasfordriver.utils.Constants.MAP_ZOOM
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MapMainFragment : Fragment() {

    private var _binding: FragmentMapMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var map: GoogleMap? = null

    private var serviceRunning = false
    private var markerPosition: Marker? = null

    private lateinit var mapBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: MapViewModel by activityViewModels()

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

        subscribeToObservers()
        btnState()
        findLocation()

        mapBottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.map_inc))


        viewModel.serviceLive.observe(viewLifecycleOwner) {
            serviceRunning = it
            btnState()
        }
    }

    private fun btnState() {
        if (serviceRunning) {
            binding.mapInc.btnStartService.visibility = View.GONE
            binding.mapInc.btnStopService.visibility = View.VISIBLE
        } else {
            binding.mapInc.btnStartService.visibility = View.VISIBLE
            binding.mapInc.btnStopService.visibility = View.GONE
        }

        binding.mapInc.btnStopService.setOnClickListener {
            sendCommandToService(Constants.ACTION_STOP_SERVICE)
        }

        binding.mapInc.btnStartService.setOnClickListener {
            sendCommandToService(Constants.ACTION_START_SERVICE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun findLocation() {
        binding.fabFindMyLocation.setOnClickListener {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { data ->
                if (data != null) {
                    val latLng = LatLng(data.latitude, data.longitude)
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM))
                } else {
                    Timber.d("Empty")
                }
            }
        }
    }

    private fun subscribeToObservers() {
        LocationTrackerService.isTracking.observe(viewLifecycleOwner) {
            viewModel.updateServiceStatus(it)
        }
        LocationTrackerService.angkotPosition.observe(viewLifecycleOwner) { data ->
            if (serviceRunning) {
                val latLng = LatLng(data.latitude, data.longitude)
                markerPosition?.remove()
                val markerOption = MarkerOptions().apply {
                    position(latLng)
                    rotation(data.bearing)
                    anchor(0.5f, 0.5f)
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.img_angkot))
                }
                markerPosition = map?.addMarker(markerOption)
                val camera = CameraPosition.builder()
                    .target(latLng)
                    .zoom(MAP_ZOOM)
                    .build()
                map?.animateCamera(CameraUpdateFactory.newCameraPosition(camera), 100, null)
            }
        }
        LocationTrackerService.userPosition.observe(viewLifecycleOwner) {

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
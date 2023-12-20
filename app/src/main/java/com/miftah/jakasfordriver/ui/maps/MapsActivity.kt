package com.miftah.jakasfordriver.ui.maps

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.miftah.jakasfordriver.R
import com.miftah.jakasfordriver.databinding.ActivityMapsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity() {

    //    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    /* private var serviceRunning = false

     private var markerPosition: Marker? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Check the position and disable swiping if needed
                binding.viewPager.isUserInputEnabled = position != 0
            }
        })
        /*val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnShareLocation.setOnClickListener {
            sendCommandToService(ACTION_START_SERVICE)
        }

        binding.btnStopShareLocation.setOnClickListener {
            sendCommandToService(ACTION_STOP_SERVICE)
        }*/
//        subscribeToObservers()
    }

    /*private fun serviceIsTracking(isTracking: Boolean) {
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
    }*/


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.map,
            R.string.transaction,
            R.string.qrCode,
        )
    }
}
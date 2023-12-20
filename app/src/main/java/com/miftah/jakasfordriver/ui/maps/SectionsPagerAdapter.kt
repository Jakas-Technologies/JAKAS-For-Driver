package com.miftah.jakasfordriver.ui.maps

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import javax.inject.Inject

class SectionsPagerAdapter @Inject constructor(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MapMainFragment()
            1 -> fragment = MapListFragment()
            2 -> fragment = QrCodeFragment()
        }
        return fragment as Fragment
    }

}
package com.miftah.jakasfordriver.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.miftah.jakasfordriver.R
import com.miftah.jakasfordriver.databinding.ActivityMainBinding
import com.miftah.jakasfordriver.ui.auth.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()

        viewModel.getSession().observe(this) {
            if (!it.isLogin) {
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }
        }
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.fragment_container_main)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
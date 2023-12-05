package com.miftah.jakasfordriver.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miftah.jakasfordriver.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
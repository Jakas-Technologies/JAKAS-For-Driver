package com.miftah.jakasfordriver.ui.auth

import androidx.lifecycle.ViewModel
import com.miftah.jakasfordriver.core.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    fun userRegis(email: String, username: String, password: String) =
        repository.userRegis(name = username, email = email, password = password)

    fun userLogin(email: String, password: String) = repository.userLogin(email, password)
}
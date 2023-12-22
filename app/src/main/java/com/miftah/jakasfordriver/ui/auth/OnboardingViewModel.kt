package com.miftah.jakasfordriver.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miftah.jakasfordriver.core.data.AppRepository
import com.miftah.jakasforpassenger.core.data.source.preference.UserPreference
import com.miftah.jakasforpassenger.core.data.source.preference.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    @Inject
    lateinit var userPreference: UserPreference

    fun userRegis(
        email: String,
        username: String,
        password: String,
        age: Int,
        licensePlate: String,
        routeName: String
    ) =
        repository.userRegis(
            name = username,
            email = email,
            password = password,
            age = age,
            licensePlate = licensePlate,
            routeName = routeName
        )

    fun createSave(id: Int, token: String) {
        viewModelScope.launch {
            userPreference.saveSession(
                UserModel(
                    id = id,
                    token = token,
                )
            )
        }
    }

    fun userLogin(email: String, password: String) = repository.userLogin(email, password)
}
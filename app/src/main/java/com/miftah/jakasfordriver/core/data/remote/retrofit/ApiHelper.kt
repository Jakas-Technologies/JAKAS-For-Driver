package com.miftah.jakasfordriver.core.data.remote.retrofit

import com.miftah.jakasfordriver.core.data.remote.request.LoginRequest
import com.miftah.jakasfordriver.core.data.remote.request.RegisterRequest
import com.miftah.jakasfordriver.core.data.remote.response.LoginResponse
import com.miftah.jakasfordriver.core.data.remote.response.RegisterResponse

interface ApiHelper {
    suspend fun login(loginRequest: LoginRequest) : LoginResponse

    suspend fun register(registerRequest: RegisterRequest) : RegisterResponse
}
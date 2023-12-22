package com.miftah.jakasfordriver.core.data.remote.retrofit

import com.miftah.jakasfordriver.core.data.remote.dto.request.LoginRequest
import com.miftah.jakasfordriver.core.data.remote.dto.request.RegisterRequest
import com.miftah.jakasfordriver.core.data.remote.dto.response.LoginResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.MidtransResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.RegisterResponse

interface ApiHelper {
    suspend fun login(loginRequest: LoginRequest) : LoginResponse

    suspend fun register(registerRequest: RegisterRequest) : RegisterResponse

    suspend fun getMidtransUpdate(): MidtransResponse
}
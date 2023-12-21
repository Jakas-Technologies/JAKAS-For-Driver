package com.miftah.jakasfordriver.core.data.remote.retrofit

import com.miftah.jakasfordriver.core.data.remote.request.LoginRequest
import com.miftah.jakasfordriver.core.data.remote.request.RegisterRequest
import com.miftah.jakasfordriver.core.data.remote.response.LoginResponse
import com.miftah.jakasfordriver.core.data.remote.response.RegisterResponse
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        apiService.login(loginRequest)

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        apiService.register(registerRequest)
}
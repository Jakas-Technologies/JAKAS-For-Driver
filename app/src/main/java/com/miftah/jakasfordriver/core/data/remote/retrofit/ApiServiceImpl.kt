package com.miftah.jakasfordriver.core.data.remote.retrofit

import com.miftah.jakasfordriver.core.data.remote.dto.request.LoginRequest
import com.miftah.jakasfordriver.core.data.remote.dto.request.RegisterRequest
import com.miftah.jakasfordriver.core.data.remote.dto.response.LoginResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.MidtransResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.RegisterResponse
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        apiService.login(loginRequest)

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        apiService.register(registerRequest)

    override suspend fun getMidtransUpdate(): MidtransResponse =
        apiService.getMidtransUpdate()
}
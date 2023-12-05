package com.miftah.jakasfordriver.core.data.remote.retrofit

import com.miftah.jakasfordriver.core.data.remote.request.LoginRequest
import com.miftah.jakasfordriver.core.data.remote.request.RegisterRequest
import com.miftah.jakasfordriver.core.data.remote.response.LoginResponse
import com.miftah.jakasfordriver.core.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse
}
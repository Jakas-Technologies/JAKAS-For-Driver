package com.miftah.jakasfordriver.core.data.remote.retrofit

import com.miftah.jakasfordriver.core.data.remote.dto.request.LoginRequest
import com.miftah.jakasfordriver.core.data.remote.dto.request.RegisterRequest
import com.miftah.jakasfordriver.core.data.remote.dto.response.LoginResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.LogoutResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.MidtransResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.QrResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("jakas/api/driver/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("jakas/api/driver/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("jakas/api/payment/driver/trip")
    suspend fun getMidtransUpdate(): MidtransResponse

    @DELETE("jakas/api/driver/{id}/logout")
    suspend fun logout(@Path("id") id: Int): LogoutResponse

    @GET("jakas/api/payment/driver/qr")
    suspend fun generateQr() : QrResponse

}
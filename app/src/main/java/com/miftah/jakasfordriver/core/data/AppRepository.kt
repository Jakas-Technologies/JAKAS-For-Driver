package com.miftah.jakasfordriver.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.miftah.jakasfordriver.core.data.remote.dto.request.LoginRequest
import com.miftah.jakasfordriver.core.data.remote.dto.request.RegisterRequest
import com.miftah.jakasfordriver.core.data.remote.dto.response.LoginResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.LogoutResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.MidtransResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.QrResponse
import com.miftah.jakasfordriver.core.data.remote.dto.response.RegisterResponse
import com.miftah.jakasfordriver.core.data.remote.retrofit.ApiService
import com.miftah.jakasfordriver.utils.Result
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun userRegis(
        name: String,
        email: String,
        password: String,
        age: Int,
        licensePlate: String,
        routeName : String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        val registerRequest = RegisterRequest(age, email, licensePlate, name, password, routeName)
        try {
            val response = apiService.register(registerRequest)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Timber.e(e)
            emit(Result.Error(e.message.toString()))
        }
    }

    fun userLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        val loginRequest = LoginRequest(email, password)
        try {
            val client = apiService.login(loginRequest)
            emit(Result.Success(client))
        } catch (e: HttpException) {
            Timber.e(e)
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getMidtransUpdate(): LiveData<Result<MidtransResponse>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.getMidtransUpdate()
            emit(Result.Success(client))
        } catch (e: HttpException) {
            Timber.e(e)
            emit(Result.Error(e.message.toString()))
        }
    }

    fun logOut(id: Int): LiveData<Result<LogoutResponse>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.logout(id)
            emit(Result.Success(data))
        } catch (e: HttpException) {
            Timber.e(e)
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getQrCode(): LiveData<Result<QrResponse>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.generateQr()
            emit(Result.Success(data))
        } catch (e: HttpException) {
            Timber.e(e)
            emit(Result.Error(e.message.toString()))
        }
    }
}
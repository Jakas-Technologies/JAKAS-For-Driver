package com.miftah.jakasfordriver.core.data.remote.dto.request

data class RegisterRequest(
    val age: Int,
    val email: String,
    val licensePlate: String,
    val name: String,
    val password: String,
    val routeName: String
)
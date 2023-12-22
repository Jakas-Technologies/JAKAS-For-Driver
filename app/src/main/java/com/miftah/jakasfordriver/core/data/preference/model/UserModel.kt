package com.miftah.jakasforpassenger.core.data.source.preference.model

data class UserModel(
    val id: Int,
    val token: String,
    val isLogin: Boolean = false
)

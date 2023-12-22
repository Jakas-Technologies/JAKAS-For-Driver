package com.miftah.jakasfordriver.core.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: UserRegister
)

data class UserRegister(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("licensePlate")
	val licensePlate: String,

	@field:SerializedName("routeId")
	val routeId: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("accessToken")
	val accessToken: Any,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("routeName")
	val routeName: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("refreshToken")
	val refreshToken: Any
)

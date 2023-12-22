package com.miftah.jakasfordriver.core.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
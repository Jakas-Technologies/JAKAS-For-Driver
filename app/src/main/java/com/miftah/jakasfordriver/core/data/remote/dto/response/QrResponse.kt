package com.miftah.jakasfordriver.core.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class QrResponse(

	@field:SerializedName("qr_code")
	val qrCode: String
)

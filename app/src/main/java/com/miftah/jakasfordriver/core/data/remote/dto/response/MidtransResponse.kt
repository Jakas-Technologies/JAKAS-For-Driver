package com.miftah.jakasfordriver.core.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class MidtransResponse(

	@field:SerializedName("totalFare")
	val totalFare: String,

	@field:SerializedName("trips")
	val trips: List<TripsItem>,

	@field:SerializedName("status")
	val status: String
)

data class TripsItem(

	@field:SerializedName("fare")
	val fare: Int,

	@field:SerializedName("isPaid")
	val isPaid: Boolean,

	@field:SerializedName("passengerName")
	val passengerName: String,

	@field:SerializedName("onProgress")
	val onProgress: Boolean,

	@field:SerializedName("driverID")
	val driverID: Int,

	@field:SerializedName("tripID")
	val tripID: Int,

	@field:SerializedName("userID")
	val userID: Int,

	@field:SerializedName("transactionID")
	val transactionID: String? = null
)

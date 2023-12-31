package com.miftah.jakasfordriver.core.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GeoGamma(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("coords")
	val coords: Coords
)

@Serializable
data class Coords(

	@field:SerializedName("altitude")
	val altitude: Double,

	@field:SerializedName("heading")
	val heading: Double,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("accuracy")
	val accuracy: Double,

	@field:SerializedName("altitudeAccuracy")
	val altitudeAccuracy: Double,

	@field:SerializedName("speed")
	val speed: Double,

	@field:SerializedName("longitude")
	val longitude: Double
)

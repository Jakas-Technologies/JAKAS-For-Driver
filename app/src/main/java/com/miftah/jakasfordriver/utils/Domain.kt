package com.miftah.jakasfordriver.utils

import com.miftah.jakasfordriver.core.data.remote.dto.response.MidtransResponse


data class Passenger(
    val driverID: Int,
    val fare: Int,
    val isPaid: Boolean,
    val onProgress: Boolean,
    val passengerName: String,
    val transactionID: String?,
    val tripID: Int,
    val userID: Int
)

fun MidtransResponse.toPassengerList() : List<Passenger> {
    val result = mutableListOf<Passenger>()
    this.trips.forEach {
        val data = Passenger(
            driverID = it.driverID,
            fare = it.fare,
            isPaid = it.isPaid,
            onProgress = it.onProgress,
            passengerName = it.passengerName,
            transactionID = it.transactionID,
            tripID = it.tripID,
            userID = it.userID
        )
        result.add(data)
    }
    return result
}
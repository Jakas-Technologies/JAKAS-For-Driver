package com.miftah.jakasfordriver.core.dummy

import com.miftah.jakasfordriver.utils.Passenger

object DummyData {
    fun dummyResultList() : List<Passenger> {
        val data = mutableListOf<Passenger>()
        for (i in 1..10) {
            data.add(
                Passenger(
                    driverID = i,
                    fare = i * 1000,
                    isPaid = false,
                    onProgress = false,
                    passengerName = "Kufaku",
                    transactionID = null,
                    tripID = i,
                    userID = i
                )
            )
        }
        return data
    }
}
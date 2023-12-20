package com.miftah.jakasfordriver.core.data.remote.socket

import com.google.android.gms.maps.model.LatLng
import com.miftah.jakasfordriver.utils.Resource

interface SocketHandlerService {

    fun initSession() : Resource<Unit>

    fun getPassengerPosition(callback: (List<String>) -> Unit)

    fun sendDriverPosition(driverPosition: LatLng)

    fun closeConnection()
}
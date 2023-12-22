package com.miftah.jakasfordriver.core.data.remote.socket

import com.google.android.gms.maps.model.LatLng
import com.miftah.jakasfordriver.core.data.remote.dto.response.GeoGamma
import com.miftah.jakasfordriver.utils.Resource

interface SocketHandlerService {

    fun initSession() : Resource<Unit>

    fun getPassengerPosition(callback: (GeoGamma) -> Unit)

    fun sendDriverPosition(driverPosition: LatLng)

    fun closeConnection()
}
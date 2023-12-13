package com.miftah.jakasfordriver.core.data.remote.socket

import com.google.android.gms.maps.model.LatLng
import com.miftah.jakasfordriver.core.data.remote.response.Coords
import com.miftah.jakasfordriver.core.data.remote.response.GeoGamma
import com.miftah.jakasfordriver.utils.Resource
import io.socket.client.IO
import io.socket.client.Socket
import timber.log.Timber
import javax.inject.Inject

class SocketHandlerImpl @Inject constructor() : SocketHandlerService {

    private var socket: Socket? = null

    override fun initSession(): Resource<Unit> {
        return try {
            val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZmlyc3ROYW1lIjoiZ2FtbWEiLCJsYXN0TmFtZSI6InJpenF1aGEiLCJlbWFpbCI6ImdhbW1hQGVtYWlsLmNvbSIsInBhc3N3b3JkIjoid29yZCIsImNyZWF0ZWRBdCI6IjIwMjMtMTItMTJUMTE6MTE6MzAuNDMxWiIsInVwZGF0ZWRBdCI6IjIwMjMtMTItMTJUMTE6MTE6MzAuNDMxWiIsImlhdCI6MTcwMjM5NTExM30.Yd0Y2Y45L0IsLrSMouC22ov3WRj6Ls3MHi2vs7Qbins"
            val option = IO.Options.builder().setExtraHeaders(mapOf("auth" to listOf("Bearer $token"))).build()
            socket = IO.socket("http://34.128.67.252:3000/",option)
            if (socket?.isActive == true) {
                Timber.d("Connect")
                Resource.Success(Unit)
            } else {
                Timber.e("Couldn't establish a connection.")
                Resource.Error("Couldn't establish a connection.")
            }
        } catch (e: Exception) {
            Timber.e(e)
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override fun getPassengerPosition(callback: (List<String>) -> Unit) {
        socket?.on("user-move") { data ->
            Timber.d("Get Loc")
            callback(data.map { it as String })
        }
    }

    override fun sendDriverPosition(driverPosition: LatLng) {
        val position = listOf(driverPosition.latitude, driverPosition.latitude)
        val jsonObject = GeoGamma(
            id = 1,
            coords = Coords(
                altitude = 1.1,
                heading = 1.1,
                latitude = driverPosition.latitude,
                accuracy = 1.1,
                altitudeAccuracy = 1.1,
                speed = 1.1,
                longitude = driverPosition.longitude
            )
        )

        Timber.d("Share Loc")
        socket?.emit("user-move", jsonObject)
    }

    override fun establishConnection() {
        socket?.connect()
    }

    override fun closeConnection() {
        socket?.disconnect()
    }
}
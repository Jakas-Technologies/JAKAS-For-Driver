package com.miftah.jakasfordriver.core.data.remote.socket

import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.miftah.jakasfordriver.core.data.remote.response.Coords
import com.miftah.jakasfordriver.core.data.remote.response.GeoGamma
import com.miftah.jakasfordriver.utils.Resource
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import timber.log.Timber
import java.net.URISyntaxException
import javax.inject.Inject

class SocketHandlerImpl @Inject constructor() : SocketHandlerService {

    private var socket: Socket? = null

    override fun initSession(): Resource<Unit> {
        return try {
            val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZmlyc3ROYW1lIjoiemlxdSIsImxhc3ROYW1lIjoiZGlzYXN0cmEiLCJlbWFpbCI6InppcXVAZW1haWwuY29tIiwicm9sZSI6ImRyaXZlciIsInBhc3N3b3JkIjoicGFzcyIsImNyZWF0ZWRBdCI6IjIwMjMtMTItMTNUMTU6NTE6MTQuMTQzWiIsInVwZGF0ZWRBdCI6IjIwMjMtMTItMTNUMTU6NTE6MTQuMTQzWiIsImlhdCI6MTcwMjk3NTQ0N30.pdSyv4Fai_2wTdM88Pk21RY6t5uWhHR0kwqd2YeZI0Y"
            val option = IO.Options.builder().setExtraHeaders(mapOf("auth" to listOf("Bearer $token"))).build()
            socket = IO.socket("https://34.128.115.212.nip.io/",option)
            socket?.connect()
            if (socket?.connected() == true) {
                Timber.d("Connect")
                Resource.Success(Unit)
            } else {
                Timber.e("Couldn't establish a connection.")
                Resource.Error("Couldn't establish a connection.")
            }
        } catch (e: URISyntaxException) {
            Timber.e(e)
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override fun getPassengerPosition(callback: (List<String>) -> Unit) {
        socket?.on("usermove") { data ->
            Timber.d("Get Loc")
//            callback(data.map { it as String })
//            val data = data[0] as JSONObject
//            Timber.d(data.getString("id"))
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

//        Timber.d("Share Loc")
        val gson = Gson()
        val data = JSONObject(gson.toJson(jsonObject))
        socket?.emit("drivermove", data)
    }

    override fun closeConnection() {
        socket?.disconnect()
    }
}
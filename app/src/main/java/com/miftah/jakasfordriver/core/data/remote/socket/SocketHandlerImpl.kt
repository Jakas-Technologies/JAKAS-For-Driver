package com.miftah.jakasfordriver.core.data.remote.socket

import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.miftah.jakasfordriver.core.data.remote.dto.response.Coords
import com.miftah.jakasfordriver.core.data.remote.dto.response.GeoGamma
import com.miftah.jakasfordriver.utils.Constants.BASE_URL
import com.miftah.jakasfordriver.utils.Resource
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import timber.log.Timber
import java.net.URISyntaxException
import javax.inject.Inject

class SocketHandlerImpl @Inject constructor(private val token : String) : SocketHandlerService {

    private var socket: Socket? = null

    override fun initSession(): Resource<Unit> {
        return try {
            val option = IO.Options.builder().setExtraHeaders(mapOf("auth" to listOf("Bearer $token"))).build()
            socket = IO.socket(BASE_URL,option)
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

    override fun getPassengerPosition(callback: (GeoGamma) -> Unit) {
        socket?.on("usermove") { data ->
            val gson = Gson()
            val json = data[0].toString()
            val geoGamma: GeoGamma = gson.fromJson(json, GeoGamma::class.java)
            callback(geoGamma)
        }
    }

    override fun sendDriverPosition(driverPosition: LatLng) {
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

        val gson = Gson()
        val data = JSONObject(gson.toJson(jsonObject))
        socket?.emit("drivermove", data)
    }

    override fun closeConnection() {
        socket?.disconnect()
    }
}
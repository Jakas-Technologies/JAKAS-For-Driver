package com.miftah.jakasfordriver.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import java.text.NumberFormat
import java.util.Locale

object Constants {
    const val BASE_URL = "http://34.128.112.136:3000/"

    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    const val KEY_MAP = "AIzaSyAttBc9n2uWPiFJ9B79kRsDVU1s6MkdEUs"

    const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"

    const val NOTIFICATION_CHANNEL_ID = "location_tracker_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Location Tracking"
    const val NOTIFICATION_ID = 1

    const val LOCATION_UPDATE_INTERVAL = 300L
    const val FASTEST_LOCATION_INTERVAL = 100L

    const val MAP_ZOOM = 20f

    const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"
    val TOKEN_KEY = stringPreferencesKey("token")
    val ID_KEY = intPreferencesKey("id")
    val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")


    fun formatToRupiah(amount: Int): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return currencyFormat.format(amount.toLong())
    }
}
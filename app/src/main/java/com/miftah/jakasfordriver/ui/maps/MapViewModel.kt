package com.miftah.jakasfordriver.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.miftah.jakasfordriver.core.data.AppRepository
import com.miftah.jakasfordriver.utils.Passenger
import com.miftah.jakasfordriver.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private var _serviceLive = MutableLiveData<Boolean>()
    val serviceLive: LiveData<Boolean> = _serviceLive

    private var _driverPosition = MutableLiveData<LatLng>()
    val driverPosition: LiveData<LatLng> = _driverPosition

    private var _transaction = MediatorLiveData<Result<List<Passenger>>>()
    val transaction : LiveData<Result<List<Passenger>>> = _transaction

    fun updateDriverPosition(newValue: LatLng) {
        _driverPosition.postValue(newValue)
    }

    fun updateServiceStatus(status: Boolean) {
        _serviceLive.postValue(status)
    }

    fun getQrCode() = repository.getQrCode()

    fun getMidtransUpdate() = repository.getMidtransUpdate()
}
package com.miftah.jakasfordriver.core.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.miftah.jakasfordriver.core.data.remote.socket.SocketHandlerImpl
import com.miftah.jakasfordriver.core.data.remote.socket.SocketHandlerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ServiceScoped
    fun provideSocketHandlerService() : SocketHandlerService = SocketHandlerImpl()

    @Provides
    @ServiceScoped
    fun provideFusedLocationProviderClient(@ApplicationContext app: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(app)
}
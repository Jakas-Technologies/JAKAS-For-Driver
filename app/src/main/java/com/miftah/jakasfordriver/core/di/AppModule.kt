package com.miftah.jakasfordriver.core.di

import com.miftah.jakasfordriver.core.data.AppRepository
import com.miftah.jakasfordriver.core.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): AppRepository = AppRepository(apiService)
}
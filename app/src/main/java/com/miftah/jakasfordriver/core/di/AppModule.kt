package com.miftah.jakasfordriver.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.android.volley.BuildConfig
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.miftah.jakasfordriver.core.data.AppRepository
import com.miftah.jakasfordriver.core.data.remote.retrofit.ApiHelper
import com.miftah.jakasfordriver.core.data.remote.retrofit.ApiService
import com.miftah.jakasfordriver.core.data.remote.retrofit.ApiServiceImpl
import com.miftah.jakasfordriver.utils.Constants
import com.miftah.jakasforpassenger.core.data.source.preference.UserPreference
import com.miftah.jakasforpassenger.core.data.source.preference.UserPreferenceImpl
import com.miftah.jakasforpassenger.core.data.source.preference.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService
    ): AppRepository = AppRepository(apiService)

    @Provides
    @Named("URL")
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor =
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

    @Provides
    @Singleton
    fun provideAuth(userPreference : UserPreference): Interceptor = Interceptor { chain ->
        val req = chain.request()

        val token = runBlocking {
            userPreference.getSession().first().token
        }

        val requestHeaders = if (token.isNotBlank()) {
            req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            req
        }
        chain.proceed(requestHeaders)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(loggingInterceptor: HttpLoggingInterceptor, interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(@Named("URL") url: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun bindApiServiceImpl(apiServiceImpl: ApiServiceImpl): ApiHelper = apiServiceImpl

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext app: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(app)


    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext app: Context): DataStore<Preferences> = app.dataStore

    @Singleton
    @Provides
    fun proveideUserPreference(userPreference: UserPreferenceImpl): UserPreference = userPreference
}
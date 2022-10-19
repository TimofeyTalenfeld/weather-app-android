package com.talenfeld.weather.main.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.talenfeld.weather.main.data.api.AccessKeyInterceptor
import com.talenfeld.weather.main.data.api.GeocodingApi
import com.talenfeld.weather.main.data.repository.AndroidLocationRepository
import com.talenfeld.weather.main.data.repository.IAndroidLocationRepository
import com.talenfeld.weather.main.data.repository.ILocationRepository
import com.talenfeld.weather.main.data.repository.LocationRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class MainProviderImpl(context: Context): MainProvider {
    @OptIn(ExperimentalSerializationApi::class)
    override val geocodingApi: GeocodingApi by lazy {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val json = Json {
            ignoreUnknownKeys = true
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_GEOCODING_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(AccessKeyInterceptor())
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        retrofit.create(GeocodingApi::class.java)
    }

    override val locationRepository: ILocationRepository by lazy {
        LocationRepository(geocodingApi)
    }

    override val androidLocationRepository: IAndroidLocationRepository by lazy {
        AndroidLocationRepository(context)
    }

    companion object {
        private const val BASE_GEOCODING_URL = "http://api.positionstack.com/v1/"
    }
}

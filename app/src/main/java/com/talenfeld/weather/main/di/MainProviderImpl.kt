package com.talenfeld.weather.main.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.talenfeld.weather.main.data.api.AccessKeyInterceptor
import com.talenfeld.weather.main.data.api.ForecastApi
import com.talenfeld.weather.main.data.api.GeocodingApi
import com.talenfeld.weather.main.data.api.configureApi
import com.talenfeld.weather.main.data.repository.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

class MainProviderImpl(
    override val context: Context
): MainProvider {

    override val geocodingApi: GeocodingApi by lazy {
        configureApi(
            baseUrl = BASE_GEOCODING_URL,
            apiServiceClass = GeocodingApi::class.java
        ) { addInterceptor(AccessKeyInterceptor()) }
    }

    override val forecastApi: ForecastApi by lazy {
        configureApi(
            baseUrl = BASE_FORECAST_URL,
            apiServiceClass = ForecastApi::class.java
        )
    }

    override val locationRepository: ILocationRepository by lazy {
        LocationRepository(geocodingApi)
    }

    override val androidLocationRepository: IAndroidLocationRepository by lazy {
        AndroidLocationRepository(context)
    }

    override val forecastRepository: IForecastRepository by lazy {
        ForecastRepository(forecastApi)
    }

    companion object {
        private const val BASE_GEOCODING_URL = "http://api.positionstack.com/v1/"
        private const val BASE_FORECAST_URL = "https://api.open-meteo.com/v1/"
    }
}

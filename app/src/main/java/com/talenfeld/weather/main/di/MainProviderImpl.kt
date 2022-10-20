package com.talenfeld.weather.main.di

import android.content.Context
import android.content.SharedPreferences
import com.talenfeld.weather.core.data.repository.CacheRepository
import com.talenfeld.weather.core.navigation.Navigator
import com.talenfeld.weather.forecast.data.repository.ForecastByLocations
import com.talenfeld.weather.forecast.data.repository.ForecastCacheRepository
import com.talenfeld.weather.main.data.api.AccessKeyInterceptor
import com.talenfeld.weather.main.data.api.ForecastApi
import com.talenfeld.weather.main.data.api.GeocodingApi
import com.talenfeld.weather.main.data.api.configureApi
import com.talenfeld.weather.main.data.repository.*
import kotlinx.serialization.json.Json

class MainProviderImpl(
    override val context: Context,
    override val navigator: Navigator,
    sharedPreferences: SharedPreferences
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

    override val forecastCacheRepository: CacheRepository<ForecastByLocations> by lazy {
        ForecastCacheRepository(
            sharedPreferences = sharedPreferences,
            json = Json
        )
    }

    companion object {
        private const val BASE_GEOCODING_URL = "http://api.positionstack.com/v1/"
        private const val BASE_FORECAST_URL = "https://api.open-meteo.com/v1/"
    }
}

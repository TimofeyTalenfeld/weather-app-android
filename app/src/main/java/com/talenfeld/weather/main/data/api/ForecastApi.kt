package com.talenfeld.weather.main.data.api

import com.talenfeld.weather.main.data.model.ForecastResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("forecast")
    fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("timezone") timezone: String = "auto",
        @Query("hourly") hourlyMode: List<String> = listOf("temperature_2m", "weathercode"),
        @Query("daily") dailyMode: List<String> = listOf("temperature_2m_min", "temperature_2m_max", "weathercode"),
        @Query("current_weather") currentWeather: Boolean = true
    ): Single<ForecastResult>
}

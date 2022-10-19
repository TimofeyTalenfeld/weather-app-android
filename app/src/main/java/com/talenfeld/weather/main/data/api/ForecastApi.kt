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
        @Query("hourly") hourlyMode: String = "temperature_2m"
    ): Single<ForecastResult>
}

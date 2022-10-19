package com.talenfeld.weather.main.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResult(
    val hourly: HourlyForecast
)

@Serializable
data class HourlyForecast(
    val time: List<String>,
    @SerialName("temperature_2m")
    val temperature: List<Float>
)

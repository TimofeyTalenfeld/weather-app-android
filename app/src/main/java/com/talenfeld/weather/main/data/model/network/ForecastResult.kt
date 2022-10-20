package com.talenfeld.weather.main.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResult(
    @SerialName("current_weather")
    val currentWeather: CurrentWeather,
    val hourly: HourlyForecast,
    val daily: DailyForecast
)

@Serializable
data class CurrentWeather(
    val time: String,
    val temperature: Float,
    @SerialName("weathercode")
    val weatherCode: Int
)

@Serializable
data class HourlyForecast(
    val time: List<String>,
    @SerialName("temperature_2m")
    val temperature: List<Float>,
    @SerialName("weathercode")
    val weatherCode: List<Int>
)

@Serializable
data class DailyForecast(
    val time: List<String>,
    @SerialName("temperature_2m_min")
    val temperatureMin: List<Float>,
    @SerialName("temperature_2m_max")
    val temperatureMax: List<Float>,
    @SerialName("weathercode")
    val weatherCode: List<Int>
)

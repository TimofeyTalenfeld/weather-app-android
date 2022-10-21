package com.talenfeld.weather.main.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ForecastCompilation(
    val currentWeather: Weather.AtMoment,
    val hourlyForecast: List<Weather.AtMoment>,
    val dailyForecast: List<Weather.Daily>
)

sealed class Weather {

    abstract val isoTime: String
    abstract val condition: Condition

    @Serializable
    data class AtMoment(
        override val isoTime: String,
        override val condition: Condition,
        val temperature: Float,
    ): Weather()

    @Serializable
    data class Daily(
        override val isoTime: String,
        override val condition: Condition,
        val temperatureMin: Float,
        val temperatureMax: Float,
    ): Weather()
}

enum class Condition {
    CLEAR,
    CLOUDY,
    RAINY
}

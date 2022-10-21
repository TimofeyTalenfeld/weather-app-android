package com.talenfeld.weather.core.ui

import android.content.Context
import com.talenfeld.weather.R
import com.talenfeld.weather.main.data.model.Condition
import kotlin.math.round

fun getConditionIconResId(condition: Condition): Int = when (condition) {
    Condition.CLEAR -> R.drawable.ic_sun
    Condition.CLOUDY -> R.drawable.ic_cloud
    Condition.RAINY -> R.drawable.ic_rain
}

fun Context.getTemperatureLabel(temperature: Float?): String = when (temperature) {
    null -> {
        getString(R.string.unknown_temperature)
    }
    else -> {
        val roundedTemperature = round(temperature).toInt()
        getString(R.string.celsius_temperature_1d, roundedTemperature)
    }
}
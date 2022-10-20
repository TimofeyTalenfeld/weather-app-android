package com.talenfeld.weather.forecast.ui

import android.content.Context
import com.talenfeld.weather.R
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.ErrorViewModel
import com.talenfeld.weather.core.ui.adapter.GalleryViewModel
import com.talenfeld.weather.core.ui.adapter.LoadingViewModel
import com.talenfeld.weather.forecast.feature.Forecast
import com.talenfeld.weather.forecast.ui.adapter.HourlyForecastViewModel
import com.talenfeld.weather.forecast.ui.adapter.LocationCardViewModel
import com.talenfeld.weather.main.data.model.Condition
import com.talenfeld.weather.main.data.model.ForecastCompilation
import com.talenfeld.weather.main.data.model.Weather
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

class ForecastViewModel(
    val listItems: List<ComparableItem>
)

class ForecastViewModelFactory(
    private val context: Context
) {
    fun create(state: Forecast.State): ForecastViewModel = when (state) {
        is Forecast.State.Loading -> createLoading()
        is Forecast.State.Error -> createError(state)
        is Forecast.State.Loaded -> createLoaded(state)
    }

    private fun createLoading(): ForecastViewModel = ForecastViewModel(listOf(LoadingViewModel))

    private fun createError(state: Forecast.State.Error): ForecastViewModel {
        val errorViewModel = when (state.cause) {
            Forecast.State.Error.Cause.GEO_PERMISSION_NEEDED -> {
                ErrorViewModel(
                    errorHintResId = R.string.permission_needed,
                    retryButtonTextResId = R.string.grant_permission
                )
            }
            Forecast.State.Error.Cause.GEO_PERMISSION_NEEDED_FROM_SETTINGS -> {
                ErrorViewModel(
                    errorHintResId = R.string.permission_needed_from_settings,
                    retryButtonTextResId = R.string.go_to_settings
                )
            }
            Forecast.State.Error.Cause.LOADING_FAILED -> {
                ErrorViewModel(
                    errorHintResId = R.string.loading_error_happened,
                    retryButtonTextResId = R.string.retry
                )
            }
        }
        return ForecastViewModel(listOf(errorViewModel))
    }

    private fun createLoaded(state: Forecast.State.Loaded): ForecastViewModel =
        ForecastViewModel(
            listOf(
                createLocationCard(state),
                createHourlyForecast(state.forecast)
            )
        )

    private fun createLocationCard(state: Forecast.State.Loaded): LocationCardViewModel {
        val conditionAnimResId = when (state.forecast.currentWeather.condition) {
            Condition.CLEAR -> R.anim.sun_animation
            Condition.CLOUDY -> R.anim.cloud_animation
            Condition.RAINY -> R.anim.rain_animation
        }
        return LocationCardViewModel(
            locationName = state.region.name,
            temperatureText = getTemperatureLabel(state.forecast.currentWeather.temperature),
            conditionIconResId = getConditionIconResId(state.forecast.currentWeather.condition),
            conditionAnimationResId = conditionAnimResId
        )
    }

    private fun createHourlyForecast(forecast: ForecastCompilation): GalleryViewModel {

        fun createHourlyForecastItem(weather: Weather.AtMoment): ComparableItem {
            val calendar = parseISOTime(weather.isoTime)
            val time = context.getString(R.string.hour_time_1d, calendar.get(Calendar.HOUR_OF_DAY))
            return HourlyForecastViewModel(
                time = time,
                conditionIconResId = getConditionIconResId(weather.condition),
                temperature = getTemperatureLabel(weather.temperature)
            )
        }

        return GalleryViewModel(
            id = HOURLY_FORECAST_GALLERY_ID,
            items = forecast.hourlyForecast.map(::createHourlyForecastItem)
        )
    }

    private fun getConditionIconResId(condition: Condition): Int = when (condition) {
        Condition.CLEAR -> R.drawable.ic_sun
        Condition.CLOUDY -> R.drawable.ic_cloud
        Condition.RAINY -> R.drawable.ic_rain
    }

    private fun getTemperatureLabel(temperature: Float): String {
        val roundedTemperature = round(temperature).toInt()
        return context.getString(R.string.celsius_temperature_1d, roundedTemperature)
    }

    private fun parseISOTime(isoTime: String): Calendar = Calendar.getInstance().apply {
        time = ISO_TIME_FORMAT.parse(isoTime)
    }

    companion object {
        private val ISO_TIME_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        private const val HOURLY_FORECAST_GALLERY_ID = "hourly_forecast"
    }
}

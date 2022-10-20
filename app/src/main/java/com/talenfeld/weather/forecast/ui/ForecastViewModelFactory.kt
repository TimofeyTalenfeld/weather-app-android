package com.talenfeld.weather.forecast.ui

import android.content.Context
import com.talenfeld.weather.R
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.ErrorViewModel
import com.talenfeld.weather.core.ui.adapter.LoadingViewModel
import com.talenfeld.weather.forecast.feature.Forecast
import com.talenfeld.weather.forecast.ui.adapter.LocationCardViewModel
import com.talenfeld.weather.main.data.model.Condition
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
            listOf(createLocationCard(state))
        )

    private fun createLocationCard(state: Forecast.State.Loaded): LocationCardViewModel {
        val currentTemperature = round(state.forecast.currentWeather.temperature).toInt()
        val (conditionIconResId, conditionAnimResId) = when (state.forecast.currentWeather.condition) {
            Condition.CLEAR -> R.drawable.ic_sun to R.anim.sun_animation
            Condition.CLOUDY -> R.drawable.ic_cloud to R.anim.cloud_animation
            Condition.RAINY -> R.drawable.ic_rain to R.anim.rain_animation
        }
        return LocationCardViewModel(
            locationName = state.region.name,
            temperatureText = context.getString(R.string.celsius_temperature_1d, currentTemperature),
            conditionIconResId = conditionIconResId,
            conditionAnimationResId = conditionAnimResId
        )
    }
}

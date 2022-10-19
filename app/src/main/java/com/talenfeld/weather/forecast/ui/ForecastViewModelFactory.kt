package com.talenfeld.weather.forecast.ui

import com.talenfeld.weather.R
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.ErrorViewModel
import com.talenfeld.weather.core.ui.adapter.LoadingViewModel
import com.talenfeld.weather.forecast.feature.Forecast

class ForecastViewModel(
    val listItems: List<ComparableItem>
)

class ForecastViewModelFactory {
    fun create(state: Forecast.State): ForecastViewModel = when (state) {
        is Forecast.State.Loading -> createLoading()
        is Forecast.State.Error -> createError(state)
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
}

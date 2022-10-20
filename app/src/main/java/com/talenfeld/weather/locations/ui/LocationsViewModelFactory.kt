package com.talenfeld.weather.locations.ui

import android.content.Context
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.LoadingViewModel
import com.talenfeld.weather.core.ui.getConditionIconResId
import com.talenfeld.weather.core.ui.getTemperatureLabel
import com.talenfeld.weather.locations.feature.Locations
import com.talenfeld.weather.locations.ui.adapter.LocationViewModel
import com.talenfeld.weather.main.data.model.ForecastCompilation
import com.talenfeld.weather.main.data.model.Region

class LocationsViewModel(val items: List<ComparableItem>)

class LocationsViewModelFactory(
    private val context: Context
) {

    fun create(state: Locations.State): LocationsViewModel = when (state) {
        is Locations.State.Loading -> createLoading()
        is Locations.State.Loaded -> createLoaded(state)
    }

    private fun createLoading() = LocationsViewModel(listOf(LoadingViewModel))

    private fun createLoaded(state: Locations.State.Loaded) =
        LocationsViewModel(
            state.locations
                .map { (location, forecast) ->
                    createLocationViewModel(location, forecast)
                }
        )

    private fun createLocationViewModel(location: Region, forecast: ForecastCompilation?): ComparableItem {
        val conditionIconResId = when (forecast) {
            null -> null
            else -> getConditionIconResId(forecast.currentWeather.condition)
        }
        return LocationViewModel(
            locationName = location.name,
            conditionIconResId = conditionIconResId,
            temperature = context.getTemperatureLabel(forecast?.currentWeather?.temperature)
        )
    }
}

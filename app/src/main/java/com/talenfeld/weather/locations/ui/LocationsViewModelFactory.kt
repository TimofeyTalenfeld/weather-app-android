package com.talenfeld.weather.locations.ui

import android.content.Context
import com.talenfeld.weather.R
import com.talenfeld.weather.core.ui.ComparableItem
import com.talenfeld.weather.core.ui.adapter.ButtonViewModel
import com.talenfeld.weather.core.ui.adapter.LoadingViewModel
import com.talenfeld.weather.core.ui.adapter.TitleViewModel
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

    private fun createLoaded(state: Locations.State.Loaded): LocationsViewModel {
        val title = TitleViewModel(context.getString(R.string.locations))
        val locations = state.locations
            . map { (location, forecast) ->
                createLocationViewModel(location, forecast)
            }
        val button = ButtonViewModel(
            id = ADD_LOCATION_BUTTON_ID,
            text = context.getString(R.string.add_location)
        )
        return LocationsViewModel(
            listOf(title) + locations + button
        )
    }

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

    companion object {
        const val ADD_LOCATION_BUTTON_ID = "add_location"
    }
}

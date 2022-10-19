package com.talenfeld.weather.forecast.di

import com.talenfeld.weather.core.di.FeatureFactory
import com.talenfeld.weather.core.feature.SimpleFeature
import com.talenfeld.weather.core.feature.effectHandler
import com.talenfeld.weather.forecast.feature.Forecast
import com.talenfeld.weather.forecast.feature.ForecastFeature
import com.talenfeld.weather.forecast.feature.LocationEffectHandler
import com.talenfeld.weather.forecast.ui.ForecastViewModelFactory
import com.talenfeld.weather.main.data.repository.IAndroidLocationRepository
import com.talenfeld.weather.main.data.repository.ILocationRepository

class ForecastFactory(
    dependencies: Dependencies
): FeatureFactory<ForecastFeature> {

    override val feature: ForecastFeature = SimpleFeature(
        initialState = Forecast.State.Loading,
        reducer = Forecast::reducer
    ).effectHandler(
        effectHandler = LocationEffectHandler(
            locationRepository = dependencies.locationRepository,
            androidLocationRepository = dependencies.androidLocationRepository
        )
    )

    val viewModelFactory = ForecastViewModelFactory()

    interface Dependencies {
        val locationRepository: ILocationRepository
        val androidLocationRepository: IAndroidLocationRepository
    }
}

package com.talenfeld.weather.forecast.di

import android.content.Context
import com.talenfeld.weather.core.di.FeatureFactory
import com.talenfeld.weather.core.feature.SimpleFeature
import com.talenfeld.weather.core.feature.effectHandler
import com.talenfeld.weather.core.navigation.Navigator
import com.talenfeld.weather.forecast.feature.Forecast
import com.talenfeld.weather.forecast.feature.ForecastFeature
import com.talenfeld.weather.forecast.feature.ForecastNavigationEffectHandler
import com.talenfeld.weather.forecast.feature.ForecastNetworkEffectHandler
import com.talenfeld.weather.forecast.ui.ForecastViewModelFactory
import com.talenfeld.weather.main.data.repository.IAndroidLocationRepository
import com.talenfeld.weather.main.data.repository.IForecastRepository
import com.talenfeld.weather.main.data.repository.ILocationRepository

class ForecastFactory(
    dependencies: Dependencies
): FeatureFactory<ForecastFeature> {

    override val feature: ForecastFeature = SimpleFeature(
        initialState = Forecast.State.Loading,
        reducer = Forecast::reducer
    ).effectHandler(
        effectHandler = ForecastNetworkEffectHandler(
            locationRepository = dependencies.locationRepository,
            androidLocationRepository = dependencies.androidLocationRepository,
            forecastRepository = dependencies.forecastRepository
        )
    ).effectHandler(
        effectHandler = ForecastNavigationEffectHandler(dependencies.navigator)
    )

    val viewModelFactory = ForecastViewModelFactory(dependencies.context)

    interface Dependencies {
        val context: Context
        val locationRepository: ILocationRepository
        val androidLocationRepository: IAndroidLocationRepository
        val forecastRepository: IForecastRepository
        val navigator: Navigator
    }
}

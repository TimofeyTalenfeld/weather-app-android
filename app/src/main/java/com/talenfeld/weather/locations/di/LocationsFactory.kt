package com.talenfeld.weather.locations.di

import android.content.Context
import com.talenfeld.weather.core.data.repository.CacheRepository
import com.talenfeld.weather.core.di.FeatureFactory
import com.talenfeld.weather.core.feature.SimpleFeature
import com.talenfeld.weather.core.feature.effectHandler
import com.talenfeld.weather.forecast.data.repository.ForecastByLocations
import com.talenfeld.weather.locations.LocationsInteractor
import com.talenfeld.weather.locations.data.repository.MockLocationsCacheRepository
import com.talenfeld.weather.locations.feature.Locations
import com.talenfeld.weather.locations.feature.LocationsDataEffectHandler
import com.talenfeld.weather.locations.feature.LocationsFeature
import com.talenfeld.weather.locations.ui.LocationsViewModelFactory
import com.talenfeld.weather.main.data.repository.IForecastRepository

class LocationsFactory(
    dependencies: Dependencies
): FeatureFactory<LocationsFeature> {

    private val locationInteractor = LocationsInteractor(
        locationsRepository = MockLocationsCacheRepository(),
        forecastCacheRepository = dependencies.forecastCacheRepository,
        forecastNetworkRepository = dependencies.forecastRepository
    )

    override val feature: LocationsFeature = SimpleFeature(
        initialState = Locations.State.Loading,
        reducer = Locations::reducer
    ).effectHandler(
        effectHandler = LocationsDataEffectHandler(
            locationsInteractor = locationInteractor
        ),
        initialEffects = setOf(Locations.Eff.LoadLocations)
    )

    val viewModelFactory: LocationsViewModelFactory =
        LocationsViewModelFactory(dependencies.context)

    interface Dependencies {
        val forecastCacheRepository: CacheRepository<ForecastByLocations>
        val forecastRepository: IForecastRepository
        val context: Context
    }
}

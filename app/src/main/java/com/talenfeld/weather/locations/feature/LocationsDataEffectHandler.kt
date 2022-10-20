package com.talenfeld.weather.locations.feature

import com.talenfeld.weather.core.feature.AsyncEffectHandler
import com.talenfeld.weather.core.feature.Disposable
import com.talenfeld.weather.core.feature.toEffectHandlerDisposable
import com.talenfeld.weather.locations.LocationsInteractor

class LocationsDataEffectHandler(
    private val locationsInteractor: LocationsInteractor
): AsyncEffectHandler<Locations.Eff, Locations.Msg>() {
    override fun invoke(eff: Locations.Eff, listener: (Locations.Msg) -> Unit): Disposable = when (eff) {
        is Locations.Eff.LoadLocations -> loadLocations(listener)
    }

    private fun loadLocations(listener: (Locations.Msg) -> Unit): Disposable =
        locationsInteractor.loadLocationsForecastFromCache()
            .mergeWith(locationsInteractor.loadLocationsForecastFromNetwork())
            .onErrorReturn { emptyList() }
            .map { Locations.Msg.OnLocationsLoaded(it) }
            .toEffectHandlerDisposable(listener)
}


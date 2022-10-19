package com.talenfeld.weather.forecast.feature

import com.talenfeld.weather.core.feature.AsyncEffectHandler
import com.talenfeld.weather.core.feature.Disposable
import com.talenfeld.weather.core.feature.EmptyDisposable
import com.talenfeld.weather.core.feature.toEffectHandlerDisposable
import com.talenfeld.weather.core.util.backgroundToUi
import com.talenfeld.weather.main.data.repository.IAndroidLocationRepository
import com.talenfeld.weather.main.data.repository.ILocationRepository

class LocationEffectHandler(
    private val androidLocationRepository: IAndroidLocationRepository,
    private val locationRepository: ILocationRepository
): AsyncEffectHandler<Forecast.Eff, Forecast.Msg>() {
    override fun invoke(eff: Forecast.Eff, listener: (Forecast.Msg) -> Unit): Disposable = when (eff) {
        is Forecast.Eff.FindRegion -> findRegion(listener)
        else -> EmptyDisposable
    }

    private fun findRegion(listener: (Forecast.Msg) -> Unit): Disposable =
        androidLocationRepository.getLastKnownLocation()
            .flatMap { location -> locationRepository.findRegion(location).backgroundToUi() }
            .map { Forecast.Msg.OnRegionFound(it) as Forecast.Msg }
            .onErrorReturn { Forecast.Msg.OnLoadingFailed }
            .toEffectHandlerDisposable(listener)
}

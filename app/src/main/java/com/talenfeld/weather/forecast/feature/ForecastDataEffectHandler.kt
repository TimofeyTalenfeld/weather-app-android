package com.talenfeld.weather.forecast.feature

import com.talenfeld.weather.core.feature.AsyncEffectHandler
import com.talenfeld.weather.core.feature.Disposable
import com.talenfeld.weather.core.feature.EmptyDisposable
import com.talenfeld.weather.core.feature.toEffectHandlerDisposable
import com.talenfeld.weather.forecast.data.interactor.CurrentForecastInteractor
import com.talenfeld.weather.forecast.data.interactor.CurrentLocationInteractor
import com.talenfeld.weather.main.data.model.Region
import com.talenfeld.weather.main.data.repository.IAndroidLocationRepository

class ForecastDataEffectHandler(
    private val androidLocationRepository: IAndroidLocationRepository,
    private val currentLocationInteractor: CurrentLocationInteractor,
    private val currentForecastInteractor: CurrentForecastInteractor,
): AsyncEffectHandler<Forecast.Eff, Forecast.Msg>() {
    override fun invoke(eff: Forecast.Eff, listener: (Forecast.Msg) -> Unit): Disposable = when (eff) {
        is Forecast.Eff.FindRegion -> findRegion(listener)
        is Forecast.Eff.LoadForecast -> loadForecast(eff.region, listener)
        else -> EmptyDisposable
    }

    private fun findRegion(listener: (Forecast.Msg) -> Unit): Disposable =
        androidLocationRepository.getLastKnownLocation()
            .flatMapObservable { location ->
                currentLocationInteractor.loadCurrentLocationFromCache(location)
                    .toObservable()
                    .mergeWith(currentLocationInteractor.loadCurrentLocationFromNetwork(location))
            }
            .map { Forecast.Msg.OnRegionFound(it) as Forecast.Msg }
            .onErrorReturn { Forecast.Msg.OnLoadingFailed }
            .toEffectHandlerDisposable(listener)

    private fun loadForecast(region: Region, listener: (Forecast.Msg) -> Unit): Disposable =
        currentForecastInteractor.loadCurrentForecastFromCache(region.location)
            .toObservable()
            .mergeWith(currentForecastInteractor.loadCurrentForecastFromNetwork(region))
            .map { forecast -> Forecast.Msg.OnForecastLoaded(forecast, region) as Forecast.Msg }
            .onErrorReturn { Forecast.Msg.OnLoadingFailed }
            .toEffectHandlerDisposable(listener)
}

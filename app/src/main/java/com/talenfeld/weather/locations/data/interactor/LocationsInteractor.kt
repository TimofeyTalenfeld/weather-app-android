package com.talenfeld.weather.locations.data.interactor

import com.talenfeld.weather.core.data.repository.CacheRepository
import com.talenfeld.weather.forecast.data.repository.ForecastByLocations
import com.talenfeld.weather.main.data.model.ForecastCompilation
import com.talenfeld.weather.main.data.model.Region
import com.talenfeld.weather.main.data.repository.IForecastRepository
import io.reactivex.Flowable
import io.reactivex.Single

class LocationsInteractor(
    private val locationsRepository: CacheRepository<List<Region>>,
    private val forecastCacheRepository: CacheRepository<ForecastByLocations>,
    private val forecastNetworkRepository: IForecastRepository
) {
    fun loadLocationsForecastFromCache(): Single<ForecastByLocations> =
        Single.zip(
            loadLocations(),
            forecastCacheRepository.get().toSingle().map { it.toMap() }.onErrorReturn { emptyMap() }
        ) { locations, forecasts -> locations.associateWith(forecasts::get).toList() }

    fun loadLocationsForecastFromNetwork(): Single<ForecastByLocations> =
        loadLocations()
            .toFlowable()
            .flatMap { locations ->
                Flowable.fromIterable(locations)
            }.flatMapSingle { location ->
                forecastNetworkRepository
                    .getForecast(location.location)
                    .map { forecast: ForecastCompilation? -> location to forecast }
            }
            .toList()
            .flatMap { forecasts ->
                forecastCacheRepository.cache(forecasts)
                    .onErrorComplete()
                    .andThen(Single.just(forecasts))
            }

    private fun loadLocations(): Single<List<Region>> =
        locationsRepository.get().toSingle().onErrorReturn { emptyList() }
}

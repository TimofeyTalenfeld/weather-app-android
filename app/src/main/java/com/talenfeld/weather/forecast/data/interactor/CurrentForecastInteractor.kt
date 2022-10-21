package com.talenfeld.weather.forecast.data.interactor

import com.talenfeld.weather.core.data.repository.CacheRepository
import com.talenfeld.weather.main.data.model.ForecastCompilation
import com.talenfeld.weather.main.data.model.Location
import com.talenfeld.weather.main.data.model.Region
import com.talenfeld.weather.main.data.repository.IForecastRepository
import io.reactivex.Maybe
import io.reactivex.Single

class CurrentForecastInteractor(
    private val currentForecastRepository: CacheRepository<Pair<Region, ForecastCompilation>>,
    private val forecastRepository: IForecastRepository
) {
    fun loadCurrentForecastFromCache(location: Location): Maybe<ForecastCompilation> =
        currentForecastRepository.get()
            .filter { (region, _) -> region.location == location }
            .map { (_, forecast) -> forecast }
            .onErrorComplete()

    fun loadCurrentForecastFromNetwork(region: Region): Single<ForecastCompilation> =
        forecastRepository.getForecast(region.location)
            .flatMap { forecast ->
                currentForecastRepository.cache(region to forecast)
                    .onErrorComplete()
                    .andThen(Single.just(forecast))
            }
}

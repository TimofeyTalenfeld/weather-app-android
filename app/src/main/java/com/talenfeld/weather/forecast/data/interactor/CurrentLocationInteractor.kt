package com.talenfeld.weather.forecast.data.interactor

import com.talenfeld.weather.core.data.repository.CacheRepository
import com.talenfeld.weather.core.util.backgroundToUi
import com.talenfeld.weather.main.data.model.ForecastCompilation
import com.talenfeld.weather.main.data.model.Location
import com.talenfeld.weather.main.data.model.Region
import com.talenfeld.weather.main.data.repository.ILocationRepository
import io.reactivex.Maybe
import io.reactivex.Single

class CurrentLocationInteractor(
    private val currentLocationRepository: CacheRepository<Pair<Region, ForecastCompilation>>,
    private val locationRepository: ILocationRepository
) {
    fun loadCurrentLocationFromCache(location: Location): Maybe<Region> =
        currentLocationRepository.get()
            .filter { (region, _) -> location == region.location }
            .map { (region, _) -> region }
            .onErrorComplete()

    fun loadCurrentLocationFromNetwork(location: Location): Single<Region> =
        locationRepository.findRegion(location).backgroundToUi()
}

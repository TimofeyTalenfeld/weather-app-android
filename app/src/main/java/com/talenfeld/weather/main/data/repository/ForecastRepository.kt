package com.talenfeld.weather.main.data.repository

import com.talenfeld.weather.main.data.api.ForecastApi
import com.talenfeld.weather.main.data.model.ForecastResult
import com.talenfeld.weather.main.data.model.Location
import io.reactivex.Single

interface IForecastRepository {
    fun getForecast(location: Location): Single<ForecastResult>
}

class ForecastRepository(
    private val api: ForecastApi
): IForecastRepository {
    override fun getForecast(location: Location): Single<ForecastResult> =
        api.getForecast(location.lat, location.lon)
}

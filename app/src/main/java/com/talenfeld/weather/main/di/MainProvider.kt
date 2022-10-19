package com.talenfeld.weather.main.di

import com.talenfeld.weather.forecast.di.ForecastFactory
import com.talenfeld.weather.main.data.api.GeocodingApi
import com.talenfeld.weather.main.data.repository.ILocationRepository

interface MainProvider: ForecastFactory.Dependencies {
    val geocodingApi: GeocodingApi

    override val locationRepository: ILocationRepository
}

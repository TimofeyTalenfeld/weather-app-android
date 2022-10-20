package com.talenfeld.weather.main.di

import com.talenfeld.weather.forecast.di.ForecastFactory
import com.talenfeld.weather.locations.di.LocationsFactory
import com.talenfeld.weather.main.data.api.ForecastApi
import com.talenfeld.weather.main.data.api.GeocodingApi

interface MainProvider:
    ForecastFactory.Dependencies,
    LocationsFactory.Dependencies
{
    val geocodingApi: GeocodingApi
    val forecastApi: ForecastApi
}

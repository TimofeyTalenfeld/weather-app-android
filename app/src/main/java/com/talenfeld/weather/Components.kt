package com.talenfeld.weather

import com.talenfeld.weather.core.di.DisposableRef
import com.talenfeld.weather.forecast.di.ForecastFactory
import com.talenfeld.weather.locations.di.LocationsFactory
import com.talenfeld.weather.main.di.MainProvider
import com.talenfeld.weather.main.di.MainProviderImpl

class Components(
    private val mainProvider: MainProvider
) {

    val forecastRef = DisposableRef { ForecastFactory(mainProvider) }

    val locationsRef = DisposableRef { LocationsFactory(mainProvider) }
}

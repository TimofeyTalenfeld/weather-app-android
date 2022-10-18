package com.talenfeld.weather

import com.talenfeld.weather.core.di.DisposableRef
import com.talenfeld.weather.forecast.di.ForecastFactory

class Components {
    val forecastRef = DisposableRef { ForecastFactory() }
}

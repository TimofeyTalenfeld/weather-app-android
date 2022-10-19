package com.talenfeld.weather

import android.app.Application
import com.talenfeld.weather.main.di.MainProviderImpl

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        COMPONENTS = Components(MainProviderImpl(baseContext))
    }

    companion object {
        lateinit var COMPONENTS: Components
    }
}

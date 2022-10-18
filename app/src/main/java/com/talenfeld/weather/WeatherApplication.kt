package com.talenfeld.weather

import android.app.Application

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        COMPONENTS = Components()
    }

    companion object {
        lateinit var COMPONENTS: Components
    }
}

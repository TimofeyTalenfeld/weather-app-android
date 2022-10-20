package com.talenfeld.weather.core.navigation

import androidx.appcompat.app.AppCompatActivity


interface RouterCommand {
    fun route(hostActivity: AppCompatActivity)
}

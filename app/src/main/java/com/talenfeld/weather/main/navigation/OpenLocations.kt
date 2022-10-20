package com.talenfeld.weather.main.navigation

import androidx.appcompat.app.AppCompatActivity
import com.talenfeld.weather.core.navigation.RouterCommand
import com.talenfeld.weather.locations.ui.LocationsFragment

class OpenLocations: RouterCommand {
    override fun route(hostActivity: AppCompatActivity) {
        LocationsFragment()
            .show(
                hostActivity.supportFragmentManager,
                LocationsFragment::class.java.simpleName
            )
    }
}
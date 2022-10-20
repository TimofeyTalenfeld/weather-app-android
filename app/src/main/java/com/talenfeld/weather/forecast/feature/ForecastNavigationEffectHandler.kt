package com.talenfeld.weather.forecast.feature

import com.talenfeld.weather.core.feature.*
import com.talenfeld.weather.core.navigation.Navigator
import com.talenfeld.weather.main.navigation.OpenLocations

class ForecastNavigationEffectHandler(
    private val navigator: Navigator
): SyncEffectHandler<Forecast.Eff, Forecast.Msg>() {
    override fun invoke(eff: Forecast.Eff, listener: (Forecast.Msg) -> Unit) = when (eff) {
        is Forecast.Eff.OpenLocations -> navigator.perform(OpenLocations())
        else -> Unit
    }
}

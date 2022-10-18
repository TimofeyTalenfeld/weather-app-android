package com.talenfeld.weather.forecast.feature

import com.talenfeld.weather.core.feature.AsyncEffectHandler
import com.talenfeld.weather.core.feature.Disposable
import com.talenfeld.weather.core.feature.EmptyDisposable

class LocationEffectHandler: AsyncEffectHandler<Forecast.Eff, Forecast.Msg>() {
    override fun invoke(eff: Forecast.Eff, listener: (Forecast.Msg) -> Unit): Disposable = when (eff) {
        else -> EmptyDisposable
    }
}

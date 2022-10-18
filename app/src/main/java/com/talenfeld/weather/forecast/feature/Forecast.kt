package com.talenfeld.weather.forecast.feature

import com.talenfeld.weather.core.feature.Feature

typealias ForecastFeature = Feature<Forecast.Msg, Forecast.State, Forecast.Eff>

object Forecast {

    sealed class Msg {
        class OnLocationPermissionResult(val granted: Boolean): Msg()
    }

    sealed class Eff {
        object CheckLocationPermission: Eff()
        object FindLocation: Eff()
    }

    sealed class State {
        object Loading: State()

        data class Error(
            val cause: Cause
        ): State() {
            enum class Cause {
                GEO_PERMISSION_NEEDED,
                LOADING_FAILED
            }
        }

        data class Loaded(
            val location: String
        )
    }

    fun reducer(msg: Msg, state: State): Pair<State, Set<Eff>> = when (msg) {
        is Msg.OnLocationPermissionResult -> onLocationResult(msg, state)
    }

    private fun onLocationResult(
        msg: Msg.OnLocationPermissionResult,
        state: State
    ): Pair<State, Set<Eff>> = when {
        msg.granted -> state to setOf(Eff.FindLocation)
        else -> State.Error(State.Error.Cause.GEO_PERMISSION_NEEDED) to emptySet()
    }
}

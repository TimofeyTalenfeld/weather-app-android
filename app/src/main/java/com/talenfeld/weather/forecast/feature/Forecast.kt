package com.talenfeld.weather.forecast.feature

import com.talenfeld.weather.core.feature.Feature

typealias ForecastFeature = Feature<Forecast.Msg, Forecast.State, Forecast.Eff>

object Forecast {

    sealed class Msg {
        class OnLocationPermissionResult(val grantStatus: GrantStatus): Msg() {
            enum class GrantStatus { GRANTED, DENIED, REQUEST_DENIED }
        }
        object OnErrorRetryClicked: Msg()
    }

    sealed class Eff {
        object RequestLocationPermission: Eff()
        object FindLocation: Eff()
    }

    sealed class State {
        object Loading: State()

        data class Error(
            val cause: Cause
        ): State() {
            enum class Cause {
                GEO_PERMISSION_NEEDED,
                GEO_PERMISSION_NEEDED_FROM_SETTINGS,
                LOADING_FAILED
            }
        }
    }

    fun reducer(msg: Msg, state: State): Pair<State, Set<Eff>> = when (msg) {
        is Msg.OnLocationPermissionResult -> onLocationResult(msg, state)
        is Msg.OnErrorRetryClicked -> onErrorRetryClicked(state)
    }

    private fun onLocationResult(
        msg: Msg.OnLocationPermissionResult,
        state: State
    ): Pair<State, Set<Eff>> = when (msg.grantStatus) {
        Msg.OnLocationPermissionResult.GrantStatus.GRANTED -> {
            state to setOf(Eff.FindLocation)
        }
        Msg.OnLocationPermissionResult.GrantStatus.DENIED -> {
            State.Error(State.Error.Cause.GEO_PERMISSION_NEEDED) to emptySet()
        }
        Msg.OnLocationPermissionResult.GrantStatus.REQUEST_DENIED -> {
            State.Error(State.Error.Cause.GEO_PERMISSION_NEEDED_FROM_SETTINGS) to emptySet()
        }
    }

    private fun onErrorRetryClicked(state: State): Pair<State, Set<Eff>> {
        val errorState = state as State.Error
        return when (errorState.cause) {
            State.Error.Cause.GEO_PERMISSION_NEEDED -> {
                State.Loading to setOf(Eff.RequestLocationPermission)
            }
            State.Error.Cause.GEO_PERMISSION_NEEDED_FROM_SETTINGS -> {
                state to emptySet()
            }
            State.Error.Cause.LOADING_FAILED -> {
                State.Loading to setOf(Eff.FindLocation)
            }
        }
    }
}

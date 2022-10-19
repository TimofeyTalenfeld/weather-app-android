package com.talenfeld.weather.forecast.feature

import com.talenfeld.weather.core.feature.Feature
import com.talenfeld.weather.main.data.model.Region

typealias ForecastFeature = Feature<Forecast.Msg, Forecast.State, Forecast.Eff>

object Forecast {

    sealed class Msg {
        class OnLocationPermissionResult(val grantStatus: GrantStatus): Msg() {
            enum class GrantStatus { GRANTED, DENIED, REQUEST_DENIED }
        }
        object OnErrorRetryClicked: Msg()

        class OnRegionFound(val region: Region): Msg()
        object OnLoadingFailed: Msg()
    }

    sealed class Eff {
        object RequestLocationPermission: Eff()
        object FindRegion: Eff()
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
        is Msg.OnRegionFound -> onRegionFound(msg, state)
        is Msg.OnLoadingFailed -> onLoadingFailed()
    }

    private fun onLocationResult(
        msg: Msg.OnLocationPermissionResult,
        state: State
    ): Pair<State, Set<Eff>> = when (msg.grantStatus) {
        Msg.OnLocationPermissionResult.GrantStatus.GRANTED -> {
            state to setOf(Eff.FindRegion)
        }
        Msg.OnLocationPermissionResult.GrantStatus.DENIED -> {
            State.Error(State.Error.Cause.GEO_PERMISSION_NEEDED) to emptySet()
        }
        Msg.OnLocationPermissionResult.GrantStatus.REQUEST_DENIED -> {
            State.Error(State.Error.Cause.GEO_PERMISSION_NEEDED_FROM_SETTINGS) to emptySet()
        }
    }

    private fun onErrorRetryClicked(state: State): Pair<State, Set<Eff>> {
        val errorState = state as? State.Error ?: return state to emptySet()
        return when (errorState.cause) {
            State.Error.Cause.GEO_PERMISSION_NEEDED -> {
                State.Loading to setOf(Eff.RequestLocationPermission)
            }
            State.Error.Cause.GEO_PERMISSION_NEEDED_FROM_SETTINGS -> {
                state to emptySet()
            }
            State.Error.Cause.LOADING_FAILED -> {
                State.Loading to setOf(Eff.FindRegion)
            }
        }
    }

    private fun onRegionFound(msg: Msg.OnRegionFound, state: State): Pair<State, Set<Eff>> {
        return state to emptySet()
    }

    private fun onLoadingFailed(): Pair<State, Set<Eff>> =
        State.Error(State.Error.Cause.LOADING_FAILED) to emptySet()
}

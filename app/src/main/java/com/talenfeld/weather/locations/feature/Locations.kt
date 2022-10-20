package com.talenfeld.weather.locations.feature

import com.talenfeld.weather.core.feature.Feature
import com.talenfeld.weather.main.data.model.ForecastCompilation
import com.talenfeld.weather.main.data.model.Region

typealias LocationsFeature = Feature<Locations.Msg, Locations.State, Locations.Eff>

object Locations {

    sealed class Msg {
        class OnLocationsLoaded(
            val locations: List<Pair<Region, ForecastCompilation?>>
        ): Msg()
    }

    sealed class Eff {
        object LoadLocations: Eff()
    }

    sealed class State {
        object Loading: State()

        data class Loaded(
            val locations: List<Pair<Region, ForecastCompilation?>>
        ): State()
    }

    fun reducer(msg: Msg, state: State): Pair<State, Set<Eff>> = when (msg) {
        is Msg.OnLocationsLoaded -> onLocationsLoaded(msg)
    }

    private fun onLocationsLoaded(msg: Msg.OnLocationsLoaded): Pair<State, Set<Eff>> =
        State.Loaded(msg.locations) to emptySet()
}

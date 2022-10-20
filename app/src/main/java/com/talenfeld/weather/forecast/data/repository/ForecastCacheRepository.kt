package com.talenfeld.weather.forecast.data.repository

import android.content.SharedPreferences
import com.talenfeld.weather.core.data.repository.CacheRepository
import com.talenfeld.weather.core.data.repository.CacheRepository.Companion.cacheToPrefs
import com.talenfeld.weather.core.data.repository.CacheRepository.Companion.getFromPrefs
import com.talenfeld.weather.main.data.model.ForecastCompilation
import com.talenfeld.weather.main.data.model.Region
import io.reactivex.Completable
import io.reactivex.Maybe
import kotlinx.serialization.json.Json

typealias ForecastByLocations = List<Pair<Region, ForecastCompilation?>>

class ForecastCacheRepository(
    private val sharedPreferences: SharedPreferences,
    private val json: Json
): CacheRepository<ForecastByLocations> {
    override fun cache(data: ForecastByLocations): Completable =
        cacheToPrefs(
            sharedPreferences = sharedPreferences,
            json = json,
            key = PREFS_KEY,
            data = data
        )

    override fun get(): Maybe<ForecastByLocations> =
        getFromPrefs(
            sharedPreferences = sharedPreferences,
            json = json,
            key = PREFS_KEY
        )

    companion object {
        private const val PREFS_KEY = "forecast"
    }
}

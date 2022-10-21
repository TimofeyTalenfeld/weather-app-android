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



class CurrentLocationForecastCacheRepository(
    private val sharedPreferences: SharedPreferences,
    private val json: Json
): CacheRepository<Pair<Region, ForecastCompilation>> {
    override fun cache(data: Pair<Region, ForecastCompilation>): Completable =
        cacheToPrefs(
            sharedPreferences = sharedPreferences,
            json = json,
            key = PREFS_KEY,
            data = data
        )

    override fun get(): Maybe<Pair<Region, ForecastCompilation>> =
        getFromPrefs(
            sharedPreferences = sharedPreferences,
            json = json,
            key = PREFS_KEY
        )

    companion object {
        private const val PREFS_KEY = "current_location_forecast"
    }
}

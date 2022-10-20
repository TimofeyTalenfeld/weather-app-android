package com.talenfeld.weather.core.data.repository

import android.content.SharedPreferences
import io.reactivex.Completable
import io.reactivex.Maybe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface CacheRepository<T> {
    fun cache(data: T): Completable
    fun get(): Maybe<T>

    companion object {
        inline fun <reified T> cacheToPrefs(
            sharedPreferences: SharedPreferences,
            json: Json,
            key: String,
            data: T
        ): Completable = Completable.fromCallable {
            val jsonString = json.encodeToString(data)
            sharedPreferences
                .edit()
                .putString(key, jsonString)
                .commit()
        }

        inline fun <reified T> getFromPrefs(
            sharedPreferences: SharedPreferences,
            json: Json,
            key: String
        ): Maybe<T> = Maybe.fromCallable {
            val jsonString = sharedPreferences.getString(key, null)
                ?: return@fromCallable null
            json.decodeFromString<T>(jsonString)
        }
    }
}

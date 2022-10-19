package com.talenfeld.weather.main.data.api

import com.talenfeld.weather.main.data.model.DecodeResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {
    @GET("reverse")
    fun reverse(@Query("query") query: String): Single<DecodeResult>
}

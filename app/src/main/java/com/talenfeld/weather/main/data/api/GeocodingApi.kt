package com.talenfeld.weather.main.data.api

import com.talenfeld.weather.main.data.model.DecodeResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {
    @GET("reverse")
    fun decode(@Query("query") query: String): Single<DecodeResult>
}

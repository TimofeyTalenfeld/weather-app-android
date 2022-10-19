package com.talenfeld.weather.main.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@OptIn(ExperimentalSerializationApi::class)
fun <T> configureApi(
    baseUrl: String,
    apiServiceClass: Class<T>,
    okhttpClientBuilder: OkHttpClient.Builder.() -> OkHttpClient.Builder = { this }
): T {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val json = Json {
        ignoreUnknownKeys = true
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(
            OkHttpClient.Builder()
                .okhttpClientBuilder()
                .addInterceptor(loggingInterceptor)
                .build()
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(apiServiceClass)
}

package com.talenfeld.weather.main.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AccessKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(ACCESS_KEY_QUERY, ACCESS_KEY_VALUE)
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val ACCESS_KEY_QUERY = "access_key"
        private const val ACCESS_KEY_VALUE = "cf9b04e6e66acf9d777f1b0d46db4514"
    }
}
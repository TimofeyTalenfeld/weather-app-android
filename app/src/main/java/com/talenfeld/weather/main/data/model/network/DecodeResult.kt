package com.talenfeld.weather.main.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DecodeResult(
    val data: List<Point>
)

@Serializable
data class Point(
    val locality: String?,
    val county: String
)

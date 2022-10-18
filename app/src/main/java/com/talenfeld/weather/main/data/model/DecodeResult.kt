package com.talenfeld.weather.main.data.model

data class DecodeResult(
    val data: List<Point>
)

data class Point(
    val country: String
)

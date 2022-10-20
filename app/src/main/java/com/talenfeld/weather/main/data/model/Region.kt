package com.talenfeld.weather.main.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Region(
    val name: String,
    val location: Location
)

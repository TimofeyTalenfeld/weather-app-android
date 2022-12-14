package com.talenfeld.weather.main.data.repository

import com.talenfeld.weather.main.data.api.GeocodingApi
import com.talenfeld.weather.main.data.model.Location
import com.talenfeld.weather.main.data.model.Region
import io.reactivex.Single

interface ILocationRepository {
    fun findRegion(location: Location): Single<Region>
}

class LocationRepository(
    private val api: GeocodingApi
): ILocationRepository {
    override fun findRegion(location: Location): Single<Region> {
        val query = "${location.lat},${location.lon}"
        return api.reverse(query)
            .map { result ->
                val point = result.data.firstOrNull()
                val regionName = point?.locality
                    ?: point?.county
                    ?: throw IllegalStateException("Region not found")
                Region(regionName, location)
            }
    }
}

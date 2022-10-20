package com.talenfeld.weather.locations.data.repository

import com.talenfeld.weather.core.data.repository.CacheRepository
import com.talenfeld.weather.main.data.model.Location
import com.talenfeld.weather.main.data.model.Region
import io.reactivex.Completable
import io.reactivex.Maybe

// repository to acquire locations from cache
// but it is mocked to make app simpler
class MockLocationsCacheRepository: CacheRepository<List<Region>> {
    override fun cache(data: List<Region>): Completable = Completable.complete()

    override fun get(): Maybe<List<Region>> = Maybe.just(MOCKED_LOCATIONS)

    companion object {
        private val MOCKED_LOCATIONS = listOf(
            Region(
                name = "New York City",
                location = Location(40.730610, -73.935242)
            ),
            Region(
                name = "Sidney",
                location = Location(-33.865143, 151.209900)
            ),
            Region(
                name = "Stockholm",
                location = Location(59.334591, 18.063240)
            ),
        )
    }
}

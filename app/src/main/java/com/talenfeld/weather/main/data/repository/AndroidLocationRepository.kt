package com.talenfeld.weather.main.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.talenfeld.weather.main.data.model.Location
import io.reactivex.Single

interface IAndroidLocationRepository {
    fun getLastKnownLocation(): Single<Location>
}

class AndroidLocationRepository(
    private val context: Context
): IAndroidLocationRepository {
    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation(): Single<Location> =
        Single.fromPublisher { subscriber ->
            val locationClient = LocationServices.getFusedLocationProviderClient(context)
            locationClient.lastLocation
                .addOnSuccessListener { androidLocation ->

                    val location = androidLocation?.let {
                        Location(
                            lat = androidLocation.latitude,
                            lon = androidLocation.longitude
                        )
                    } ?: FALLBACK_LOCATION
                    subscriber.onNext(location)
                }
                .addOnFailureListener { e -> subscriber.onError(e) }
                .addOnCompleteListener { subscriber.onComplete() }
        }

    companion object {
        // fallback location of Klarna office in case we cannot find actual location :)
        private val FALLBACK_LOCATION = Location(
            lat = 59.3371443,
            lon = 18.0621663
        )
    }
}

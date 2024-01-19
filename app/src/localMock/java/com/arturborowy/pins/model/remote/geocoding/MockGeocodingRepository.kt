package com.arturborowy.pins.model.remote.geocoding

import com.arturborowy.pins.model.remote.places.MockInputException
import com.arturborowy.pins.model.remote.places.MockPlacesPredictionRepository
import com.ultimatelogger.android.output.ALog
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MockGeocodingRepository : GeocodingRepository {

    override suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double) =
        suspendCoroutine {
            ALog.d("latitude: $latitude, longitude: $longitude")

            if (latitude == MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.latitude
                && longitude == MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.longitude
            ) {
                val country = GEOCODED_COUNTRY

                ALog.d("result: $country")
                it.resume(country)
            } else {
                it.resumeWithException(MockInputException())
            }
        }

    companion object {
        val GEOCODED_COUNTRY = CountryDto("pl", "Poland")
    }
}
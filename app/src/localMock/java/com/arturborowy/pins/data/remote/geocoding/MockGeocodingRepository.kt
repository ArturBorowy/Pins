package com.arturborowy.pins.data.remote.geocoding

import com.arturborowy.pins.data.remote.places.MockInputException
import com.arturborowy.pins.data.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.domain.Country
import com.ultimatelogger.android.output.ALog
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MockGeocodingRepository : GeocodingRepository {

    override suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double) =
        suspendCancellableCoroutine {
            ALog.d("latitude: $latitude, longitude: $longitude")

            if (isLatLongMatchingMockLocations(latitude, longitude)) {
                val country = GEOCODED_COUNTRY

                ALog.d("result: $country")
                it.resume(country)
            } else {
                it.resumeWithException(MockInputException())
            }
        }

    private fun isLatLongMatchingMockLocations(latitude: Double, longitude: Double) =
        (latitude == MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.latitude
                && longitude == MockPlacesPredictionRepository.FETCHED_PLACE_DETAILS.longitude)
                ||
                (latitude == MockPlacesPredictionRepository.ALTERNATIVE_FETCHED_PLACE_DETAILS.latitude
                        && longitude == MockPlacesPredictionRepository.ALTERNATIVE_FETCHED_PLACE_DETAILS.longitude)


    companion object {
        val GEOCODED_COUNTRY = CountryDto(Country.Id("pl"), "Poland")
    }
}

package com.arturborowy.pins.model.remote.geocoding

import android.location.Geocoder
import com.ultimatelogger.android.output.ALog
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidGeocoderRepository @Inject constructor(
    private val geocoder: Geocoder,
) : GeocodingRepository {

    override suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double) =
        suspendCoroutine {
            ALog.d("latitude: $latitude, longitude: $longitude")
            val address = geocoder.getFromLocation(latitude, longitude, 1)
                ?.firstOrNull()
            ALog.d("fetched address: $address")

            val country = CountryDto(
                address!!.countryCode,
                address.countryName
            )
            ALog.d("result: $country")
            it.resume(country)
        }
}
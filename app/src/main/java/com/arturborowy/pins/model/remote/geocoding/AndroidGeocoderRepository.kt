package com.arturborowy.pins.model.remote.geocoding

import android.location.Geocoder
import com.ultimatelogger.android.output.ALog
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AndroidGeocoderRepository @Inject constructor(
    private val geocoder: Geocoder,
) : GeocodingRepository {

    override suspend fun getCountryOfGivenLocationName(locationName: String) =
        suspendCoroutine {
            ALog.d("locationName: $locationName")
            val address = geocoder.getFromLocationName(locationName, 1)
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
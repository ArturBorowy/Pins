package com.arturborowy.pins.model.remote.geocoding

import android.location.Geocoder
import com.arturborowy.pins.data.remote.geocoding.CountryDto
import com.arturborowy.pins.data.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.domain.Country
import com.ultimatelogger.android.output.ALog
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AndroidGeocoderRepository @Inject constructor(
    private val geocoder: Geocoder,
) : GeocodingRepository {

    override suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double): CountryDto =
        suspendCancellableCoroutine {
            ALog.d("latitude: $latitude, longitude: $longitude")
            val address = geocoder.getFromLocation(latitude, longitude, 1)
                ?.firstOrNull()
            ALog.d("fetched address: $address")

            val country = CountryDto(
                Country.Id(address!!.countryCode),
                address.countryName
            )
            ALog.d("result: $country")
            it.resume(country)
        }
}

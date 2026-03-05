package com.arturborowy.pins.model.remote.geocoding

import android.location.Address
import android.location.Geocoder
import com.ultimatelogger.android.output.ALog
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidGeocoderRepository @Inject constructor(
    private val geocoder: Geocoder,
) : GeocodingRepository {

    override suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double): CountryDto =
        suspendCancellableCoroutine { continuation ->
            ALog.d("latitude: $latitude, longitude: $longitude")

            geocoder.getFromLocation(latitude, longitude, 1, object : Geocoder.GeocodeListener {
                override fun onGeocode(addresses: MutableList<Address>) {
                    val address = addresses.firstOrNull()
                    ALog.d("fetched address: $address")
                    val country = CountryDto(address!!.countryCode, address.countryName)
                    ALog.d("result: $country")
                    continuation.resume(country)
                }

                override fun onError(errorMessage: String?) {
                    continuation.resumeWithException(Exception(errorMessage))
                }
            })
        }
}

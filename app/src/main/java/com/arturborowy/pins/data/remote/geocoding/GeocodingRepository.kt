package com.arturborowy.pins.data.remote.geocoding

interface GeocodingRepository {

    suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double): CountryDto
}

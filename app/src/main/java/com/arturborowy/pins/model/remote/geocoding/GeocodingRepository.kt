package com.arturborowy.pins.model.remote.geocoding

interface GeocodingRepository {

    suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double): CountryDto
}
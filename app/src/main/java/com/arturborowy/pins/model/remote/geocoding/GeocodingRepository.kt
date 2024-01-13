package com.arturborowy.pins.model.remote.geocoding

interface GeocodingRepository {

    suspend fun getCountryOfGivenLocationName(locationName: String): CountryDto
}
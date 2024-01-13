package com.arturborowy.pins.domain

data class PlaceDetails(
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val country: Country
)

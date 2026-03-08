package com.arturborowy.pins.domain

import java.util.Date

data class StopDetails(
    val arrivalDate: Date,
    val departureDate: Date?,
    val placeDetailsWithCountry: PlaceDetailsWithCountry
)

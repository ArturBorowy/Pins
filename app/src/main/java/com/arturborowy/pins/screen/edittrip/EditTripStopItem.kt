package com.arturborowy.pins.screen.edittrip

import com.arturborowy.pins.domain.Country

data class EditTripStopItem(
    val locationName: String,
    val arrivalDateStr: String,
    val departureDateStr: String?,
    val latitude: Double,
    val longitude: Double,
    val country: Country,
)
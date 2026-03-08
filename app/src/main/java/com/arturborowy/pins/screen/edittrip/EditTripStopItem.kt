package com.arturborowy.pins.screen.edittrip

import androidx.compose.runtime.Immutable
import com.arturborowy.pins.domain.Country

@Immutable
data class EditTripStopItem(
    val locationName: String,
    val arrivalDateStr: String,
    val departureDateStr: String?,
    val latitude: Double,
    val longitude: Double,
    val country: Country,
)
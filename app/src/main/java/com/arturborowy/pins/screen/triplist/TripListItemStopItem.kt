package com.arturborowy.pins.screen.triplist

import androidx.compose.runtime.Immutable
import com.arturborowy.pins.domain.Country

@Immutable
data class TripListItemStopItem(
    val locationName: String,
    val dateStr: String,
    val country: Country,
)

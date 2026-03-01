package com.arturborowy.pins.screen.triplist

import com.arturborowy.pins.domain.Country

data class TripListItemStopItem(
    val locationName: String,
    val dateStr: String,
    val country: Country,
)
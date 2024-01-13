package com.arturborowy.pins.screen.pinslist

import com.arturborowy.pins.domain.Country

data class TripSingleStop(
    val id: Long,
    val name: String,
    val locationName: String,
    val dateStr: String,
    val country: Country,
)

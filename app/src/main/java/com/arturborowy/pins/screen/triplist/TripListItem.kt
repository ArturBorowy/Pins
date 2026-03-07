package com.arturborowy.pins.screen.triplist

import com.arturborowy.pins.domain.Trip

data class TripListItem(
    val id: Trip.Id,
    val name: String,
    val stops: List<TripListItemStopItem>
)

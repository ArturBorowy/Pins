package com.arturborowy.pins.screen.triplist

data class TripListItem(
    val id: Long,
    val name: String,
    val stops: List<TripListItemStopItem>
)

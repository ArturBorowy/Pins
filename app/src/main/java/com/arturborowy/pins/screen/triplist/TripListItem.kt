package com.arturborowy.pins.screen.triplist

import androidx.compose.runtime.Immutable
import com.arturborowy.pins.domain.Trip

@Immutable
data class TripListItem(
    val id: Trip.Id,
    val name: String,
    val stops: List<TripListItemStopItem>
)

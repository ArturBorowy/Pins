package com.arturborowy.pins.model.places

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaceDetails(
    @PrimaryKey val id: String,
    val latitude: Double,
    val longitude: Double,
    val label: String
)

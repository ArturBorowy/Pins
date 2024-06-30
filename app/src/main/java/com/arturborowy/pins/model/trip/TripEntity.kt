package com.arturborowy.pins.model.trip

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TripEntity(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

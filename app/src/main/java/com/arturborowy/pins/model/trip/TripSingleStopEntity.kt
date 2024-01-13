package com.arturborowy.pins.model.trip

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arturborowy.pins.domain.Country

@Entity
data class TripSingleStopEntity(
    val name: String,
    val locationName: String,
    val arrivalDate: Long,
    val departureDate: Long? = null,
    val latitude: Double,
    val longitude: Double,
    @Embedded val country: Country,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

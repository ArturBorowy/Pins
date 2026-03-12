package com.arturborowy.pins.data.trip

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.arturborowy.pins.domain.Country

@Entity(
    foreignKeys = [ForeignKey(
        entity = TripEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("tripId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("tripId")]
)
data class StopEntity(
    val locationName: String,
    val arrivalDate: Long,
    val departureDate: Long?,
    val latitude: Double,
    val longitude: Double,
    @Embedded val country: Country,
    val tripId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

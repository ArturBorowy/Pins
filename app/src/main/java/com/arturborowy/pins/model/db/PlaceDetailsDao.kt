package com.arturborowy.pins.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arturborowy.pins.model.trip.TripSingleStopEntity
import com.arturborowy.pins.screen.pinslist.TripSingleStop

@Dao
interface PlaceDetailsDao {

    @Query("SELECT * FROM tripSingleStopEntity WHERE id=:id ")
    suspend fun select(id: String): TripSingleStopEntity


    @Query("SELECT * FROM tripSingleStopEntity")
    suspend fun select(): List<TripSingleStopEntity>

    @Insert
    suspend fun insert(tripSingleStop: TripSingleStopEntity)

    @Query("DELETE FROM tripSingleStopEntity WHERE id=:id")
    suspend fun removePlaceDetails(id: String)
}
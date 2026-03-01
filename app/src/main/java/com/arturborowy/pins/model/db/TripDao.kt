package com.arturborowy.pins.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arturborowy.pins.model.trip.TripEntity

@Dao
interface TripDao {

    @Query("SELECT * FROM tripEntity WHERE id=:id ")
    suspend fun select(id: String): TripEntity

    @Query("SELECT * FROM tripEntity")
    suspend fun select(): List<TripEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tripEntity: TripEntity): Long

    @Query("DELETE FROM tripEntity WHERE id=:id")
    suspend fun remove(id: Long)
}
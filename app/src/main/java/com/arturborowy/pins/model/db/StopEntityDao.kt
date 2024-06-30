package com.arturborowy.pins.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arturborowy.pins.model.trip.StopEntity

@Dao
interface StopEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stopEntity: StopEntity)

    @Query("SELECT * FROM stopEntity WHERE tripId=:tripId ")
    suspend fun select(tripId: Long): List<StopEntity>
}
package com.arturborowy.pins.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arturborowy.pins.data.trip.StopEntity

@Dao
interface StopEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stopEntity: StopEntity)

    @Query("SELECT * FROM stopEntity WHERE tripId=:tripId ")
    suspend fun select(tripId: Long): List<StopEntity>
}
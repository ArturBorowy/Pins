package com.arturborowy.pins.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arturborowy.pins.model.places.PlaceDetails

@Dao
interface PlaceDetailsDao {

    @Query("SELECT * FROM placedetails WHERE id=:id ")
    suspend fun select(id: String): PlaceDetails


    @Query("SELECT * FROM placedetails")
    suspend fun select(): List<PlaceDetails>

    @Insert
    suspend fun insert(placeDetails: PlaceDetails)
}
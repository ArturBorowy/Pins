package com.arturborowy.pins.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arturborowy.pins.model.places.PlaceDetails

@Database(entities = [PlaceDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDetailsDao(): PlaceDetailsDao

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "db")
                .build()
    }
}
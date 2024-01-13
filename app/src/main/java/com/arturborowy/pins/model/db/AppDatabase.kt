package com.arturborowy.pins.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arturborowy.pins.model.trip.TripSingleStopEntity

@Database(entities = [TripSingleStopEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placeDetailsDao(): PlaceDetailsDao

    companion object {
        const val NAME = "db"

        fun build(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, NAME)
                .build()
    }
}
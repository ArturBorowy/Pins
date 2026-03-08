package com.arturborowy.pins.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arturborowy.pins.data.trip.StopEntity
import com.arturborowy.pins.data.trip.TripEntity

@Database(
    entities = [TripEntity::class, StopEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun stopEntityDao(): StopEntityDao

    companion object {
        const val NAME = "db"

        fun build(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, NAME).build()
    }
}
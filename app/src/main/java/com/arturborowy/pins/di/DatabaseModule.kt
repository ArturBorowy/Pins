package com.arturborowy.pins.di

import android.content.Context
import com.arturborowy.pins.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun appDatabase(@ApplicationContext context: Context) = AppDatabase.build(context)

    @Provides
    fun tripDao(appDatabase: AppDatabase) = appDatabase.tripDao()

    @Provides
    fun stopEntityDao(appDatabase: AppDatabase) = appDatabase.stopEntityDao()
}

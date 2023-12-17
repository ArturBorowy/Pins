package com.arturborowy.pins.di

import android.content.Context
import com.arturborowy.pins.model.db.AppDatabase
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ExternalModule {

    @Provides
    fun placesClient(@ApplicationContext context: Context): PlacesClient =
        Places.createClient(context)

    @Provides
    fun appDatabase(@ApplicationContext context: Context) = AppDatabase.build(context)

    @Provides
    fun placesDetailsDao(appDatabase: AppDatabase) = appDatabase.placeDetailsDao()
}
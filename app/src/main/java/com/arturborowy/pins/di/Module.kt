package com.arturborowy.pins.di

import android.content.Context
import com.arturborowy.pins.model.db.AppDatabase
import com.arturborowy.pins.model.db.PlaceDetailsDao
import com.arturborowy.pins.model.places.GooglePlacesRepository
import com.arturborowy.pins.model.places.PlacesRepository
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object Module {

    @Provides
    fun placesClient(@ApplicationContext context: Context): PlacesClient =
        Places.createClient(context)

    @Provides
    fun placesRepository(
        placeDetailsDao: PlaceDetailsDao,
        placesClient: PlacesClient
    ): PlacesRepository =
        GooglePlacesRepository(placeDetailsDao, placesClient)

    @Provides
    fun placesDetailsDao(appDatabase: AppDatabase) = appDatabase.placeDetailsDao()

    @Provides
    fun appDatabase(@ApplicationContext context: Context) = AppDatabase.build(context)
}
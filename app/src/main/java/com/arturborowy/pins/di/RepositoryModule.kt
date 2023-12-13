package com.arturborowy.pins.di

import com.arturborowy.pins.model.db.AppDatabase
import com.arturborowy.pins.model.places.GooglePlacesClientRepository
import com.arturborowy.pins.model.places.PlacesPredictionRepository
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun placesPredictionRepository(placesClient: PlacesClient): PlacesPredictionRepository =
        GooglePlacesClientRepository(placesClient)

    @Provides
    fun placesDetailsDao(appDatabase: AppDatabase) = appDatabase.placeDetailsDao()
}
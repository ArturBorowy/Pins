package com.arturborowy.pins.di

import android.content.Context
import android.location.Geocoder
import com.arturborowy.pins.model.db.AppDatabase
import com.arturborowy.pins.model.places.GooglePlacesClientRepository
import com.arturborowy.pins.model.places.PlacesPredictionRepository
import com.arturborowy.pins.ui.Navigator
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun placesPredictionRepository(
        geocoder: Geocoder,
        placesClient: PlacesClient,
    ): PlacesPredictionRepository =
        GooglePlacesClientRepository(geocoder, placesClient)

    @Provides
    fun geocoder(@ApplicationContext context: Context) = Geocoder(context)
}
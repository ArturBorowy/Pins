package com.arturborowy.pins.di

import com.arturborowy.pins.model.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.model.remote.geocoding.MockGeocodingRepository
import com.arturborowy.pins.model.remote.places.MockPlacesPredictionRepository
import com.arturborowy.pins.model.remote.places.PlacesPredictionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    fun placesPredictionRepository(): PlacesPredictionRepository =
        MockPlacesPredictionRepository()

    @Provides
    fun geocodingRepository(): GeocodingRepository =
        MockGeocodingRepository()
}
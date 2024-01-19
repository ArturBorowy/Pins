package com.arturborowy.pins.di

import android.location.Geocoder
import com.arturborowy.pins.model.remote.geocoding.AndroidGeocoderRepository
import com.arturborowy.pins.model.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.model.remote.places.GooglePlacesClientRepository
import com.arturborowy.pins.model.remote.places.PlacesPredictionRepository
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    fun placesPredictionRepository(placesClient: PlacesClient): PlacesPredictionRepository =
        GooglePlacesClientRepository(placesClient)

    @Provides
    fun geocodingRepository(geocoder: Geocoder): GeocodingRepository =
        AndroidGeocoderRepository(geocoder)
}
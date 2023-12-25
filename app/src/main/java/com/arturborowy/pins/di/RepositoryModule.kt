package com.arturborowy.pins.di

import android.content.Context
import android.location.Geocoder
import com.arturborowy.pins.model.places.CountryIconsRepository
import com.arturborowy.pins.model.places.GooglePlacesClientRepository
import com.arturborowy.pins.model.places.PlacesPredictionRepository
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun placesPredictionRepository(
        geocoder: Geocoder,
        placesClient: PlacesClient,
        countryIconsRepository: CountryIconsRepository
    ): PlacesPredictionRepository =
        GooglePlacesClientRepository(geocoder, placesClient, countryIconsRepository)

    @Provides
    fun countryIconsRepository() = CountryIconsRepository()

    @Provides
    fun geocoder(@ApplicationContext context: Context) = Geocoder(context)
}
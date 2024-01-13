package com.arturborowy.pins.di

import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.model.countryicons.CountryIconsRepository
import com.arturborowy.pins.model.db.PlaceDetailsDao
import com.arturborowy.pins.model.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.model.remote.places.PlacesPredictionRepository
import com.arturborowy.pins.ui.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun placesInteractor(
        placeDetailsDao: PlaceDetailsDao,
        placesPredictionRepository: PlacesPredictionRepository,
        geocodingRepository: GeocodingRepository,
        countryIconsRepository: CountryIconsRepository
    ) =
        PlacesInteractor(
            placeDetailsDao,
            placesPredictionRepository,
            geocodingRepository,
            countryIconsRepository
        )

    @Singleton
    @Provides
    fun navigator() = Navigator()
}
package com.arturborowy.pins.di

import com.arturborowy.pins.model.db.PlaceDetailsDao
import com.arturborowy.pins.model.places.PlacesInteractor
import com.arturborowy.pins.model.places.PlacesPredictionRepository
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
        placesPredictionRepository: PlacesPredictionRepository
    ) =
        PlacesInteractor(placeDetailsDao, placesPredictionRepository)

    @Singleton
    @Provides
    fun navigator() = Navigator()
}
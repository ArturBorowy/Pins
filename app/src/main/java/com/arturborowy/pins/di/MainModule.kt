package com.arturborowy.pins.di

import com.arturborowy.pins.model.db.PlaceDetailsDao
import com.arturborowy.pins.model.places.PlacesInteractor
import com.arturborowy.pins.model.places.PlacesPredictionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    fun placesInteractor(
        placeDetailsDao: PlaceDetailsDao,
        placesPredictionRepository: PlacesPredictionRepository
    ) =
        PlacesInteractor(placeDetailsDao, placesPredictionRepository)
}
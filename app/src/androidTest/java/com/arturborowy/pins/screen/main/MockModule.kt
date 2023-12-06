package com.arturborowy.pins.screen.main

import com.arturborowy.pins.di.Module
import com.arturborowy.pins.model.places.AddressPrediction
import com.arturborowy.pins.model.places.PlaceDetails
import com.arturborowy.pins.model.places.PlacesRepository
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@dagger.Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [Module::class]
)
object MockModule {

    @Provides
    fun placesRepository() = object : PlacesRepository {
        override suspend fun getAddressPredictions(inputString: String): List<AddressPrediction> {
            TODO("Not yet implemented")
        }

        override suspend fun getPlaceDetails(id: String): PlaceDetails {
            TODO("Not yet implemented")
        }

        override suspend fun savePlace(placeDetails: PlaceDetails) {
            TODO("Not yet implemented")
        }

        override suspend fun getPlaces(): List<PlaceDetails> {
            TODO("Not yet implemented")
        }
    }
}
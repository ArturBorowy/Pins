package com.arturborowy.pins.screen.main

import com.arturborowy.pins.R
import com.arturborowy.pins.di.RepositoryModule
import com.arturborowy.pins.model.places.AddressPrediction
import com.arturborowy.pins.model.places.CountryEntity
import com.arturborowy.pins.model.places.PlaceDetails
import com.arturborowy.pins.model.places.PlacesPredictionRepository
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk

@dagger.Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object MockModule {

    val ADDRESS_PREDICION_LABEL = "LABEL KOMUNY PARYSKIEJ"

    val country = CountryEntity("COUNTRYID", "COUNTRYNAME", R.drawable.pl)
    val PLACE_ID = "ID"

    val PLACE_DETAILS =
        PlaceDetails(
            PLACE_ID,
            1.0,
            2.0,
            ADDRESS_PREDICION_LABEL,
            country
        )

    val ADDRESS_PREDICTION = AddressPrediction("ID", ADDRESS_PREDICION_LABEL)

    @Provides
    fun placesPredictionRepository() = mockk<PlacesPredictionRepository>().apply {
        coEvery { getPlaceDetails(PLACE_ID) } returns PLACE_DETAILS
        coEvery { getAddressPredictions(ADDRESS_PREDICION_LABEL) } returns listOf(ADDRESS_PREDICTION)
    }
}
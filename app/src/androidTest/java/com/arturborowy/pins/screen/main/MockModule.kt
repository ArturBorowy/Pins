package com.arturborowy.pins.screen.main

import com.arturborowy.pins.di.RepositoryModule
import com.arturborowy.pins.model.db.PlaceDetailsDao
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

    val country = CountryEntity("COUNTRYID", "COUNTRYNAME")

    val PLACE_DETAILS =
        PlaceDetails(
            "id",
            1.0,
            2.0,
            ADDRESS_PREDICION_LABEL,
            country
        )

    val PLACE_QUERY_TO_PREDICT = "Komuny Paryskiej St."
    val PLACE_ID = "ID"

    val ADDRESS_PREDICTION = AddressPrediction("ID", ADDRESS_PREDICION_LABEL)

    @Provides
    fun placesPredictionRepository() = placesPredictionRepository

    val placesPredictionRepository = mockk<PlacesPredictionRepository>().apply {
        coEvery { getPlaceDetails(PLACE_ID) } returns PLACE_DETAILS
        coEvery { getAddressPredictions(PLACE_QUERY_TO_PREDICT) } returns listOf(ADDRESS_PREDICTION)
    }

    @Provides
    fun placesDetailsDao() = placeDetailsDao

    val placeDetailsDao = mockk<PlaceDetailsDao>().apply {
        coEvery { insert(PLACE_DETAILS) } returns Unit
        coEvery { select() } returns listOf(PLACE_DETAILS)
    }
}
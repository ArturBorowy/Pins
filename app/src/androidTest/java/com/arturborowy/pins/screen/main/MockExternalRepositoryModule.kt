package com.arturborowy.pins.screen.main

import com.arturborowy.pins.di.RemoteRepositoryModule
import com.arturborowy.pins.model.remote.geocoding.CountryDto
import com.arturborowy.pins.model.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.model.remote.places.PlaceDetailsDto
import com.arturborowy.pins.model.remote.places.PlacesPredictionRepository
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk

@dagger.Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteRepositoryModule::class]
)
object MockExternalRepositoryModule {

    val ADDRESS_PREDICION_LABEL = "LABEL KOMUNY PARYSKIEJ"
    val TRIP_NAME = "BEST TRIP EVER"

    val country = CountryDto("cy", "CYPRUS")
    val PLACE_ID = "ID"

    val PLACE_DETAILS =
        PlaceDetailsDto(
            ADDRESS_PREDICION_LABEL,
            1.0,
            2.0
        )

    val ADDRESS_PREDICTION = AddressPredictionDto("ID", ADDRESS_PREDICION_LABEL)

    @Provides
    fun placesPredictionRepository() = mockk<PlacesPredictionRepository>().apply {
        coEvery { getPlaceDetails(PLACE_ID) } returns PLACE_DETAILS
        coEvery { getAddressPredictions(ADDRESS_PREDICION_LABEL) } returns listOf(ADDRESS_PREDICTION)
    }

    @Provides
    fun geocodingRepository() = mockk<GeocodingRepository>().apply {
        coEvery { getCountryOfGivenLocationName(ADDRESS_PREDICION_LABEL) } returns country
    }
}
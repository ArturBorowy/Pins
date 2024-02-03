package com.arturborowy.pins.di

import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.domain.licences.LicencesInteractor
import com.arturborowy.pins.model.countryicons.CountryIconsRepository
import com.arturborowy.pins.model.db.PlaceDetailsDao
import com.arturborowy.pins.model.licences.LibrariesRepository
import com.arturborowy.pins.model.licences.LicencesContentRepository
import com.arturborowy.pins.model.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.model.remote.places.PlacesPredictionRepository
import com.arturborowy.pins.model.system.ResourcesRepository
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

    @Provides
    fun licencesInteractor(
        librariesRepository: LibrariesRepository,
        licencesContentRepository: LicencesContentRepository,
        resourcesRepository: ResourcesRepository
    ) = LicencesInteractor(
        resourcesRepository,
        librariesRepository,
        licencesContentRepository
    )

    @Singleton
    @Provides
    fun navigator() = Navigator()
}
package com.arturborowy.pins.di

import com.arturborowy.pins.domain.Licenses.LicensesInteractor
import com.arturborowy.pins.domain.PlacesInteractor
import com.arturborowy.pins.model.Licenses.LibrariesRepository
import com.arturborowy.pins.model.Licenses.LicensesContentRepository
import com.arturborowy.pins.model.countryicons.CountryIconsRepository
import com.arturborowy.pins.model.db.StopEntityDao
import com.arturborowy.pins.model.db.TripDao
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
        tripDao: TripDao,
        stopEntityDao: StopEntityDao,
        placesPredictionRepository: PlacesPredictionRepository,
        geocodingRepository: GeocodingRepository,
        countryIconsRepository: CountryIconsRepository
    ) =
        PlacesInteractor(
            tripDao,
            stopEntityDao,
            placesPredictionRepository,
            geocodingRepository,
            countryIconsRepository
        )

    @Provides
    fun LicensesInteractor(
        librariesRepository: LibrariesRepository,
        LicensesContentRepository: LicensesContentRepository,
        resourcesRepository: ResourcesRepository
    ) = LicensesInteractor(
        resourcesRepository,
        librariesRepository,
        LicensesContentRepository
    )

    @Singleton
    @Provides
    fun navigator() = Navigator()
}
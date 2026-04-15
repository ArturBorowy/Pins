package com.arturborowy.pins.di

import android.content.Context
import com.arturborowy.pins.flags.CountryIconsRepository
import com.arturborowy.pins.data.db.StopEntityDao
import com.arturborowy.pins.data.db.TripDao
import com.arturborowy.pins.data.licences.LibrariesRepository
import com.arturborowy.pins.data.licences.LicencesContentRepository
import com.arturborowy.pins.data.system.BuildInfoRepository
import com.arturborowy.pins.data.system.ResourcesRepository
import com.arturborowy.pins.data.trip.TripDaoRepository
import com.arturborowy.pins.domain.TripRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun countryIconsRepository() = CountryIconsRepository()

    @Provides
    fun resourcesRepository(@ApplicationContext context: Context) =
        ResourcesRepository(context)

    @Provides
    fun buildInfoRepository() =
        BuildInfoRepository()

    @Provides
    fun licencesRepository(resourcesRepository: ResourcesRepository) =
        LicencesContentRepository(resourcesRepository)

    @Provides
    fun librariesRepository() =
        LibrariesRepository

    @Provides
    fun tripRepository(tripDao: TripDao, stopEntityDao: StopEntityDao): TripRepository =
        TripDaoRepository(tripDao, stopEntityDao)
}

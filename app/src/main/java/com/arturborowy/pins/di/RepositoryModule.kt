package com.arturborowy.pins.di

import android.content.Context
import com.arturborowy.pins.model.countryicons.CountryIconsRepository
import com.arturborowy.pins.model.licences.LibrariesRepository
import com.arturborowy.pins.model.licences.LicencesContentRepository
import com.arturborowy.pins.model.system.BuildInfoRepository
import com.arturborowy.pins.model.system.ResourcesRepository
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
}

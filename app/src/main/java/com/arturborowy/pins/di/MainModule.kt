package com.arturborowy.pins.di

import com.arturborowy.pins.data.licences.LibrariesRepository
import com.arturborowy.pins.data.licences.LicencesContentRepository
import com.arturborowy.pins.data.system.ResourcesRepository
import com.arturborowy.pins.domain.licences.LicencesInteractor
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

package com.arturborowy.pins.di

import com.arturborowy.pins.data.Licenses.LicensesContentRepository
import com.arturborowy.pins.data.licences.LibrariesRepository
import com.arturborowy.pins.data.system.ResourcesRepository
import com.arturborowy.pins.domain.licences.LicensesInteractor
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
    fun LicensesInteractor(
        librariesRepository: LibrariesRepository,
        licensesContentRepository: LicensesContentRepository,
        resourcesRepository: ResourcesRepository
    ) = LicensesInteractor(
        resourcesRepository,
        librariesRepository,
        licensesContentRepository
    )

    @Singleton
    @Provides
    fun navigator() = Navigator()
}
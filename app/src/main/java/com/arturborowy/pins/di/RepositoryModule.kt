package com.arturborowy.pins.di

import android.content.Context
import com.arturborowy.pins.model.ResourcesRepository
import com.arturborowy.pins.model.countryicons.CountryIconsRepository
import com.arturborowy.pins.model.system.BuildInfoRepository
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
}

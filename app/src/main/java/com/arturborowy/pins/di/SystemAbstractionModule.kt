package com.arturborowy.pins.di

import com.arturborowy.pins.model.system.LocaleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SystemAbstractionModule {

    @Provides
    fun localeRepository() = LocaleRepository()
}
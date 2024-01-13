package com.arturborowy.pins.screen.main

import com.arturborowy.pins.di.SystemAbstractionModule
import com.arturborowy.pins.model.system.LocaleRepository
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import java.util.Locale

@dagger.Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SystemAbstractionModule::class]
)
object MockSystemAbstractionModule {

    @Provides
    fun localeRepository() = mockk<LocaleRepository>().apply {
        coEvery { locale } returns Locale.US
    }
}
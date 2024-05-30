package com.arturborowy.pins.screen.main

import com.arturborowy.pins.model.system.LocaleRepository
import com.arturborowy.pins.model.system.NetworkStateRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale

object MockSystemAbstractionModule {

    val localeRepository = mockk<LocaleRepository>().apply {
        every { locale } returns Locale.US
    }

    val networkStateRepository = mockk<NetworkStateRepository>().apply {
        every { hasInternet } returns MutableStateFlow(true)
    }
}
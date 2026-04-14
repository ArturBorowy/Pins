package com.arturborowy.pins.screen.settings.appearance

import com.arturborowy.pins.data.AppVisualTheme
import com.arturborowy.pins.data.UserSettingsRepository
import com.arturborowy.pins.ui.Navigator
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppearanceSettingsViewModelTest {

    private val navigator: Navigator = mockk(relaxed = true)
    private val userSettingsRepository: UserSettingsRepository = mockk(relaxed = true)

    private val themeFlow = MutableStateFlow(AppVisualTheme.FOLLOW_SYSTEM)
    private val dynamicColorsFlow = MutableStateFlow(false)

    private lateinit var viewModel: AppearanceSettingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        every { userSettingsRepository.getAppVisualTheme() } returns themeFlow
        every { userSettingsRepository.getUseDynamicColors() } returns dynamicColorsFlow
        viewModel = AppearanceSettingsViewModel(navigator, userSettingsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun state_hasDefaultValues_initially() {
        assertEquals(AppVisualTheme.FOLLOW_SYSTEM, viewModel.state.value.selectedTheme)
        assertFalse(viewModel.state.value.isDynamicColorsEnabled)
    }

    @Test
    fun state_reflectsTheme_dark_fromRepository() = runTest {
        themeFlow.emit(AppVisualTheme.DARK)
        assertEquals(AppVisualTheme.DARK, viewModel.state.value.selectedTheme)
    }

    @Test
    fun state_reflectsTheme_light_fromRepository() = runTest {
        themeFlow.emit(AppVisualTheme.LIGHT)
        assertEquals(AppVisualTheme.LIGHT, viewModel.state.value.selectedTheme)
    }

    @Test
    fun state_reflectsDynamicColors_whenEnabled() = runTest {
        dynamicColorsFlow.emit(true)
        assertTrue(viewModel.state.value.isDynamicColorsEnabled)
    }

    @Test
    fun state_reflectsDynamicColors_whenDisabled() = runTest {
        dynamicColorsFlow.emit(true)
        dynamicColorsFlow.emit(false)
        assertFalse(viewModel.state.value.isDynamicColorsEnabled)
    }

    @Test
    fun onThemeSelected_callsRepository_withCorrectTheme() = runTest {
        viewModel.onThemeSelected(AppVisualTheme.DARK)
        coVerify { userSettingsRepository.setAppVisualTheme(AppVisualTheme.DARK) }
    }

    @Test
    fun onDynamicColorsToggled_callsRepository_withTrue() = runTest {
        viewModel.onDynamicColorsToggled(true)
        coVerify { userSettingsRepository.setUseDynamicColors(true) }
    }

    @Test
    fun onDynamicColorsToggled_callsRepository_withFalse() = runTest {
        viewModel.onDynamicColorsToggled(false)
        coVerify { userSettingsRepository.setUseDynamicColors(false) }
    }
}

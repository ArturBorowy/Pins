package com.arturborowy.pins.screen.settings.appearance

import com.arturborowy.pins.data.AppVisualTheme
import com.arturborowy.pins.data.UserSettingsRepository
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.utils.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppearanceSettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val navigator: Navigator = mockk(relaxed = true)
    private val userSettingsRepository: UserSettingsRepository = mockk(relaxed = true)

    private val themeFlow = MutableStateFlow(AppVisualTheme.FOLLOW_SYSTEM)
    private val dynamicColorsFlow = MutableStateFlow(false)

    private lateinit var viewModel: AppearanceSettingsViewModel

    @Before
    fun setUp() {
        every { userSettingsRepository.getAppVisualTheme() } returns themeFlow
        every { userSettingsRepository.getUseDynamicColors() } returns dynamicColorsFlow
        viewModel = AppearanceSettingsViewModel(navigator, userSettingsRepository)
    }

    @Test
    fun state_hasDefaultValues_initially() {
        assertEquals(AppVisualTheme.FOLLOW_SYSTEM, viewModel.state.value.selectedTheme)
        assertFalse(viewModel.state.value.isDynamicColorsEnabled)
    }

    @Test
    fun state_reflectsTheme_dark_fromRepository() = runTest(mainDispatcherRule.testDispatcher) {
        backgroundScope.launch { viewModel.state.collect {} }

        themeFlow.emit(AppVisualTheme.DARK)
        assertEquals(AppVisualTheme.DARK, viewModel.state.value.selectedTheme)
    }

    @Test
    fun state_reflectsTheme_light_fromRepository() = runTest(mainDispatcherRule.testDispatcher) {
        backgroundScope.launch { viewModel.state.collect {} }

        themeFlow.emit(AppVisualTheme.LIGHT)
        assertEquals(AppVisualTheme.LIGHT, viewModel.state.value.selectedTheme)
    }

    @Test
    fun state_reflectsDynamicColors_whenEnabled() = runTest(mainDispatcherRule.testDispatcher) {
        backgroundScope.launch { viewModel.state.collect {} }

        dynamicColorsFlow.emit(true)
        assertTrue(viewModel.state.value.isDynamicColorsEnabled)
    }

    @Test
    fun state_reflectsDynamicColors_whenDisabled() = runTest(mainDispatcherRule.testDispatcher) {
        backgroundScope.launch { viewModel.state.collect {} }

        dynamicColorsFlow.emit(true)
        dynamicColorsFlow.emit(false)
        assertFalse(viewModel.state.value.isDynamicColorsEnabled)
    }

    @Test
    fun onThemeSelected_callsRepository_withCorrectTheme() =
        runTest(mainDispatcherRule.testDispatcher) {
            viewModel.onThemeSelected(AppVisualTheme.DARK)
            coVerify { userSettingsRepository.setAppVisualTheme(AppVisualTheme.DARK) }
        }

    @Test
    fun onDynamicColorsToggled_callsRepository_withTrue() =
        runTest(mainDispatcherRule.testDispatcher) {
            viewModel.onDynamicColorsToggled(true)
            coVerify { userSettingsRepository.setUseDynamicColors(true) }
        }

    @Test
    fun onDynamicColorsToggled_callsRepository_withFalse() =
        runTest(mainDispatcherRule.testDispatcher) {
            viewModel.onDynamicColorsToggled(false)
            coVerify { userSettingsRepository.setUseDynamicColors(false) }
        }
}

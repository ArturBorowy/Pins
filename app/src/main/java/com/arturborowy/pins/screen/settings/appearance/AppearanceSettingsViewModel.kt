package com.arturborowy.pins.screen.settings.appearance

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.data.AppVisualTheme
import com.arturborowy.pins.data.UserSettingsRepository
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppearanceSettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val userSettingsRepository: UserSettingsRepository
) : BaseViewModel() {

    val state: StateFlow<State> = combine(
        userSettingsRepository.getAppVisualTheme(),
        userSettingsRepository.getUseDynamicColors()
    ) { theme, dynamicColors ->
        State(
            selectedTheme = theme,
            isDynamicColorsEnabled = dynamicColors
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = State()
    )

    fun onThemeSelected(theme: AppVisualTheme) {
        viewModelScope.launch {
            userSettingsRepository.setAppVisualTheme(theme)
        }
    }

    fun onDynamicColorsToggled(enabled: Boolean) {
        viewModelScope.launch {
            userSettingsRepository.setUseDynamicColors(enabled)
        }
    }

    @Immutable
    data class State(
        val selectedTheme: AppVisualTheme = AppVisualTheme.FOLLOW_SYSTEM,
        val isDynamicColorsEnabled: Boolean = false
    )
}

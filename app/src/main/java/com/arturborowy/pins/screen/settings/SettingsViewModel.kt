package com.arturborowy.pins.screen.settings

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.data.AppVisualTheme
import com.arturborowy.pins.data.UserSettingsRepository
import com.arturborowy.pins.data.system.BuildInfoRepository
import com.arturborowy.pins.ui.NavigationTarget
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
class SettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val buildInfoRepository: BuildInfoRepository,
    private val userSettingsRepository: UserSettingsRepository
) : BaseViewModel() {

    val state: StateFlow<State> = combine(
        userSettingsRepository.getAppVisualTheme(),
        userSettingsRepository.getUseDynamicColors()
    ) { theme, dynamicColors ->
        State(
            versionNumber = buildInfoRepository.buildVersion,
            selectedTheme = theme,
            isDynamicColorsEnabled = dynamicColors
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = State(versionNumber = buildInfoRepository.buildVersion)
    )

    fun onLicencesClick() {
        viewModelScope.launch {
            navigator.navigateTo(NavigationTarget.Licences)
        }
    }

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
        val versionNumber: String,
        val selectedTheme: AppVisualTheme = AppVisualTheme.FOLLOW_SYSTEM,
        val isDynamicColorsEnabled: Boolean = false
    )
}

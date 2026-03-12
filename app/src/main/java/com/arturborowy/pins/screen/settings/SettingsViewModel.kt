package com.arturborowy.pins.screen.settings

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.data.system.BuildInfoRepository
import com.arturborowy.pins.ui.NavigationTarget
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val buildInfoRepository: BuildInfoRepository,
) : BaseViewModel() {

    val state = MutableStateFlow(State(buildInfoRepository.buildVersion))

    fun onLicencesClick() {
        viewModelScope.launch {
            navigator.navigateTo(NavigationTarget.Licences)
        }
    }

    fun onThemeSelected(theme: ThemeOption) { /* TODO */
    }

    fun onDynamicColorsToggled(enabled: Boolean) { /* TODO */
    }

    enum class ThemeOption { LIGHT, DARK, SYSTEM }

    @Immutable
    data class State(
        val versionNumber: String,
        val selectedTheme: ThemeOption = ThemeOption.SYSTEM,
        val isDynamicColorsEnabled: Boolean = false
    )
}
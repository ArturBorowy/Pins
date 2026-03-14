package com.arturborowy.pins.screen.settings

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.data.system.BuildInfoRepository
import com.arturborowy.pins.ui.NavigationTarget
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val buildInfoRepository: BuildInfoRepository
) : BaseViewModel() {

    val state: StateFlow<State> = MutableStateFlow(buildInfoRepository.buildVersion)
        .map { State(versionNumber = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = State(versionNumber = buildInfoRepository.buildVersion)
        )

    fun onAppearanceClick() {
        viewModelScope.launch {
            navigator.navigateTo(NavigationTarget.AppearanceSettings)
        }
    }

    fun onLicencesClick() {
        viewModelScope.launch {
            navigator.navigateTo(NavigationTarget.Licences)
        }
    }

    @Immutable
    data class State(
        val versionNumber: String
    )
}

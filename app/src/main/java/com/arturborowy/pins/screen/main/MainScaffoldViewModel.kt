package com.arturborowy.pins.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturborowy.pins.data.AppVisualTheme
import com.arturborowy.pins.data.UserSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScaffoldViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) : ViewModel() {

    val state: StateFlow<State> = combine(
        userSettingsRepository.getAppVisualTheme(),
        userSettingsRepository.getUseDynamicColors()
    ) { theme, dynamicColors ->
        State(appVisualTheme = theme, useDynamicColors = dynamicColors)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = State()
    )

    data class State(
        val appVisualTheme: AppVisualTheme = AppVisualTheme.FOLLOW_SYSTEM,
        val useDynamicColors: Boolean = false
    )
}

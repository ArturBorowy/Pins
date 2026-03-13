package com.arturborowy.pins.data

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsRepository @Inject constructor(
    private val userSettingsDataStore: UserSettingsDataStore
) {

    companion object {
        private val DEFAULT_APP_VISUAL_THEME = AppVisualTheme.FOLLOW_SYSTEM
        private const val DEFAULT_USE_DYNAMIC_COLORS = false
    }

    fun getAppVisualTheme() =
        userSettingsDataStore.appVisualThemeFlow()
            .map {
                if (it == null) {
                    DEFAULT_APP_VISUAL_THEME
                } else {
                    AppVisualTheme.valueOf(it)
                }
            }

    suspend fun setAppVisualTheme(newValue: AppVisualTheme) {
        userSettingsDataStore.setAppVisualTheme(newValue.name)
    }

    fun getUseDynamicColors() =
        userSettingsDataStore.useDynamicColorsFlow()
            .map { it ?: DEFAULT_USE_DYNAMIC_COLORS }

    suspend fun setUseDynamicColors(newValue: Boolean) {
        userSettingsDataStore.setUseDynamicColors(newValue)
    }
}
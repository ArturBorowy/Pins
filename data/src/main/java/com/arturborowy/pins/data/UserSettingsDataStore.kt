package com.arturborowy.pins.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val APP_VISUAL_THEME = stringPreferencesKey("APP_VISUAL_THEME")
        private val USE_DYNAMIC_COLORS = booleanPreferencesKey("USE_DYNAMIC_COLORS")
    }

    fun appVisualThemeFlow() = dataStore.data.map { preferences ->
        preferences[APP_VISUAL_THEME]
    }

    suspend fun setAppVisualTheme(newValue: String) {
        dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[APP_VISUAL_THEME] = newValue
            }
        }
    }

    fun useDynamicColorsFlow() = dataStore.data.map { preferences ->
        preferences[USE_DYNAMIC_COLORS]
    }

    suspend fun setUseDynamicColors(newValue: Boolean) {
        dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[USE_DYNAMIC_COLORS] = newValue
            }
        }
    }
}

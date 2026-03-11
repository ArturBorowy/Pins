package com.arturborowy.pins.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val APP_THEME = stringPreferencesKey("APP_THEME")
    }

    fun appThemeFlow() = dataStore.data.map { preferences ->
        preferences[APP_THEME]
    }

    suspend fun setAppTheme(newValue: String) {
        dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[APP_THEME] = newValue
            }
        }
    }
}
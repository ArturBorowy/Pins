package com.arturborowy.pins.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.ultimatelogger.android.output.ALog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun userSettingsDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.userSettingsDataStore
    }
}

private const val USER_SETTINGS_FILE_NAME = "userSettings"

// We need it handled by delegate instead of factory to avoid creating new hilt component per
// every test = multiple instances of DataStore handling same file
private val Context.userSettingsDataStore by preferencesDataStore(
    name = USER_SETTINGS_FILE_NAME,
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    corruptionHandler = ReplaceFileCorruptionHandler {
        ALog.e(it, "$USER_SETTINGS_FILE_NAME DataStore corruption!")
        emptyPreferences()
    }
)

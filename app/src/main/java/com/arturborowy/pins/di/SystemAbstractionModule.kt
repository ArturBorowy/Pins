package com.arturborowy.pins.di

import android.content.Context
import android.net.ConnectivityManager
import com.arturborowy.pins.data.system.LocaleRepository
import com.arturborowy.pins.data.system.NetworkStateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SystemAbstractionModule {

    @Provides
    fun localeRepository() = LocaleRepository()

    @Provides
    fun networkStateRepository(connectivityManager: ConnectivityManager) =
        NetworkStateRepository(connectivityManager)

    @Provides
    fun connectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
}
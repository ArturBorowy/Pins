package com.arturborowy.pins.di

import android.content.Context
import android.location.Geocoder
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun placesClient(@ApplicationContext context: Context): PlacesClient =
        Places.createClient(context)

    @Provides
    fun geocoder(@ApplicationContext context: Context) = Geocoder(context)
}
package com.arturborowy.pins

import android.app.Application
import com.google.android.libraries.places.api.Places

class PinsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Places.initialize(this, getString(R.string.maps_api_key))
    }
}
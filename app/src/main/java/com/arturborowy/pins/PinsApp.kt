package com.arturborowy.pins

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.ultimatelogger.android.output.ALogInitializer
import com.ultimatelogger.multiplatform.tag.TagSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PinsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        Places.initialize(this, BuildConfig.MAPS_API_KEY)
    }

    private fun initLogger() {
        val shouldLog = BuildConfig.DEBUG
        val defaultTagSettings = TagSettings(
            shouldLogFileNameAndLineNum = true,
            shouldLogClassName = true,
            shouldLogMethodName = true,
            shouldLogThreadName = false
        )

        ALogInitializer.init(shouldLog, defaultTagSettings)
    }

    override fun onTerminate() {
        ALogInitializer.destroy()
        super.onTerminate()
    }
}

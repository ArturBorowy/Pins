package com.arturborowy.pins.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arturborowy.pins.screen.edittrip.EditTripViewModel
import com.arturborowy.pins.screen.map.MapViewModel
import com.arturborowy.pins.ui.Navigator
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun mapViewModelFactory(): MapViewModel.AssistedFactory
        fun editTripViewModelFactory(): EditTripViewModel.AssistedFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            MainScaffold(navigator)
        }
    }
}

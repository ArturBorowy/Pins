package com.arturborowy.pins

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.arturborowy.pins.addpin.AddPinScreen
import com.arturborowy.pins.main.MainScreen
import com.arturborowy.pins.pinslist.PinsListScreen

enum class Screen(val composable: @Composable (NavController) -> Unit) {
    MAIN({ MainScreen(it) }),
    ADD_PIN({ AddPinScreen() }),
    PINS_LIST({ PinsListScreen() }),
}
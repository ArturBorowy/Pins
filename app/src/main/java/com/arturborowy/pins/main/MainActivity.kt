package com.arturborowy.pins.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arturborowy.pins.addpin.AddPinScreen
import com.arturborowy.pins.pinslist.PinsListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Home") {
                composable("Home") { MainScreen(navController) }
                composable("AddPin") { AddPinScreen() }
                composable("PinsList") { PinsListScreen() }
            }
        }
    }
}
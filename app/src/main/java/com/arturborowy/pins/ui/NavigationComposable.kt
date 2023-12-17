package com.arturborowy.pins.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arturborowy.pins.screen.addpin.AddPinScreen
import com.arturborowy.pins.screen.main.BottomNavItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavigationComposable(
    navController: NavHostController,
    navigator: Navigator,
    innerPadding: PaddingValues
) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach {
            navController.navigate(it.label)
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MAP.label,
        modifier = Modifier.padding(innerPadding)
    ) {
        BottomNavItem.values().forEach { screen ->
            composable(screen.name) { screen.screenComposable() }
        }

        val args = listOf(navArgument(NavigationTarget.EDIT_PIN.PLACE_ID_KEY) {
            type = NavType.StringType
        })

        composable(NavigationTarget.EDIT_PIN.label, arguments = args) {
            AddPinScreen(placeId = it.arguments?.getString(NavigationTarget.EDIT_PIN.PLACE_ID_KEY))
        }
    }
}
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
import com.arturborowy.pins.screen.edittrip.EditTripScreen
import com.arturborowy.pins.screen.edittrip.editTripViewModel
import com.arturborowy.pins.screen.main.BottomNavItem
import com.arturborowy.pins.screen.map.MapScreen
import com.arturborowy.pins.screen.map.mapViewModel
import com.arturborowy.pins.screen.settings.licenses.LicensesScreen
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

        navigator.back.onEach {
            if (it) {
                navController.popBackStack()
            }
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = BottomNavItem.MAP.label,
        modifier = Modifier.padding(innerPadding)
    ) {
        BottomNavItem.entries.forEach { screen ->
            composable(screen.name) { screen.screenComposable() }
        }

        composable(NavigationTarget.Licenses.label) { LicensesScreen() }

        val mapArgs = listOf(navArgument(NavigationTarget.ADD_TRIP.SHOW_TRIP_TYPE_BAR_KEY) {
            type = NavType.BoolType
        })
        composable(NavigationTarget.ADD_TRIP.label, mapArgs) {
            MapScreen(mapViewModel(it.arguments?.getBoolean(NavigationTarget.ADD_TRIP.SHOW_TRIP_TYPE_BAR_KEY)!!))
        }

        val editTripArgs = listOf(navArgument(NavigationTarget.EDIT_TRIP.TRIP_ID_KEY) {
            type = NavType.StringType
        })
        composable(NavigationTarget.EDIT_TRIP.label, editTripArgs) {
            EditTripScreen(editTripViewModel(it.arguments?.getString(NavigationTarget.EDIT_TRIP.TRIP_ID_KEY)!!))
        }
    }
}
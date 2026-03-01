package com.arturborowy.pins.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arturborowy.pins.ui.NavigationComposable
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.ui.composable.BottomNavigationBar
import com.arturborowy.pins.ui.theme.PinsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navigator: Navigator) {
    val navController = rememberNavController()

    val backStackEntry = navController.currentBackStackEntryAsState()

    PinsTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = PinsTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        backStackEntry.value?.destination?.route,
                        navController::navigate
                    )
                },
                content = { innerPadding ->
                    NavigationComposable(
                        navController = navController,
                        navigator = navigator,
                        innerPadding = innerPadding
                    )
                })
        }
    }
}


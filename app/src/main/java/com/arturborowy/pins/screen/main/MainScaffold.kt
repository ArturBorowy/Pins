package com.arturborowy.pins.screen.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.data.AppVisualTheme
import com.arturborowy.pins.ui.NavigationComposable
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.ui.composable.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navigator: Navigator,
    viewModel: MainScaffoldViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val useDarkTheme = when (state.appVisualTheme) {
        AppVisualTheme.DARK -> true
        AppVisualTheme.LIGHT -> false
        AppVisualTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }

    BrandTheme(
        useDarkTheme = useDarkTheme,
        useDynamicColors = state.useDynamicColors
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BrandTheme.colorScheme.background
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

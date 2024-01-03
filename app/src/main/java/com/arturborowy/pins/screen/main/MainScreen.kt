package com.arturborowy.pins.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.NavigationComposable
import com.arturborowy.pins.ui.Navigator
import com.arturborowy.pins.ui.theme.PinsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigator: Navigator) {
    val navController = rememberNavController()
    PinsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = { BottomAppBar(navController) },
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

@Composable
fun BottomAppBar(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = Modifier.height(70.dp),
        containerColor = colorResource(R.color.white),
    ) {
        BottomNavItem.values().forEach { item ->
            val selected = item.name == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.primary),
                    unselectedIconColor = colorResource(R.color.primary_light),
                    indicatorColor = Color.White
                ),
                onClick = { navController.navigate(item.name) },
                icon = {
                    Icon(
                        modifier = Modifier.padding(0.dp, 8.dp),
                        painter = painterResource(item.iconResId),
                        contentDescription = item.name,
                    )
                })
        }
    }
}
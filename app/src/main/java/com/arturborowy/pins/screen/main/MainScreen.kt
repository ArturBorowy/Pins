package com.arturborowy.pins.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arturborowy.pins.ui.theme.PinsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    PinsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(bottomBar = { BottomAppBar(navController) },
                content = { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.MAP.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        BottomNavItem.values().forEach { screen ->
                            composable(screen.name) { screen.screenComposable() }
                        }
                    }
                })
        }
    }
}

@Composable
fun BottomAppBar(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        BottomNavItem.values().forEach { item ->
            val selected = item.name == backStackEntry.value?.destination?.route

            NavigationBarItem(selected = selected,
                onClick = { navController.navigate(item.name) },
                label = {
                    Text(
                        text = stringResource(item.textResId),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        tint = Color.White,
                        contentDescription = "${item.name} Icon",
                    )
                })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
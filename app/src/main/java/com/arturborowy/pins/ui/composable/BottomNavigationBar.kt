package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.screen.main.BottomNavItem
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.spacing

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigationItemClick: (route: String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .shadow(10.dp)
            .background(PinsTheme.colorScheme.surface)
            .navigationBarsPadding()
            .height(65.dp),
        containerColor = PinsTheme.colorScheme.surface,
    ) {
        BottomNavItem.entries.forEach { item ->
            val selected = item.name == currentRoute

            NavigationBarItem(
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PinsTheme.colorScheme.primary,
                    unselectedIconColor = PinsTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = PinsTheme.colorScheme.primaryContainer
                ),
                onClick = { onNavigationItemClick(item.name) },
                icon = {
                    Icon(
                        modifier = Modifier.padding(vertical = PinsTheme.spacing.small),
                        painter = painterResource(item.iconResId),
                        contentDescription = item.name,
                    )
                })
        }
    }
}

@Preview
@Composable
fun BottomNavigationBar() = PreviewTheme {
    BottomNavigationBar(
        BottomNavItem.MAP.label,
        {}
    )
}
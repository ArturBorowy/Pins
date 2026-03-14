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
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.screen.main.BottomNavItem

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigationItemClick: (route: String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .shadow(BrandTheme.sizing.bottomBarShadow)
            .background(BrandTheme.colorScheme.surface)
            .navigationBarsPadding()
            .height(BrandTheme.sizing.bottomBarHeight),
        containerColor = BrandTheme.colorScheme.surface,
    ) {
        BottomNavItem.entries.forEach { item ->
            val selected = item.name == currentRoute

            NavigationBarItem(
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BrandTheme.colorScheme.primary,
                    unselectedIconColor = BrandTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = BrandTheme.colorScheme.primaryContainer
                ),
                onClick = { onNavigationItemClick(item.name) },
                icon = {
                    Icon(
                        modifier = Modifier.padding(vertical = BrandTheme.spacing.textSpacing),
                        painter = painterResource(item.iconResId),
                        contentDescription = item.name,
                    )
                })
        }
    }
}

@Preview
@Composable
private fun BottomNavigationBar() = PreviewTheme {
    BottomNavigationBar(
        BottomNavItem.MAP.label,
        {}
    )
}
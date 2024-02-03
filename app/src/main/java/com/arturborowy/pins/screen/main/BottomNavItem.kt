package com.arturborowy.pins.screen.main

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.map.MapScreen
import com.arturborowy.pins.screen.pinslist.PinsListScreen
import com.arturborowy.pins.screen.settings.SettingsScreen
import com.arturborowy.pins.ui.NavigationTarget

enum class BottomNavItem(
    @DrawableRes val iconResId: Int,
    val screenComposable: @Composable () -> Unit
) : NavigationTarget {
    MAP(R.drawable.ic_bottom_nav_map, { MapScreen() }),
    PIN_LIST(R.drawable.ic_bottom_nav_list, { PinsListScreen() }),
    SETTINGS(R.drawable.ic_bottom_nav_settings, { SettingsScreen() });

    override val label = name
}

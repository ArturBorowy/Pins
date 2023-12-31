package com.arturborowy.pins.screen.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.addpin.AddPinScreen
import com.arturborowy.pins.screen.map.MapScreen
import com.arturborowy.pins.screen.pinslist.PinsListScreen
import com.arturborowy.pins.ui.NavigationTarget

enum class BottomNavItem(
    @StringRes val textResId: Int,
    val icon: ImageVector,
    val screenComposable: @Composable () -> Unit
) : NavigationTarget {
    MAP(R.string.main_bottom_nav_label_home, Icons.Rounded.Home, { MapScreen() }),
    ADD_PIN(R.string.main_bottom_nav_label_add, Icons.Rounded.AddCircle, { AddPinScreen() }),
    PINS_LIST(R.string.main_bottom_nav_label_list, Icons.Rounded.List, { PinsListScreen() });

    override val label = name
}

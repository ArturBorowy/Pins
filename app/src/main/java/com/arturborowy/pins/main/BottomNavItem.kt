package com.arturborowy.pins.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arturborowy.pins.R
import com.arturborowy.pins.addpin.AddPinScreen
import com.arturborowy.pins.map.MapScreen
import com.arturborowy.pins.pinslist.PinsListScreen

enum class BottomNavItem(
    @StringRes val textResId: Int,
    val icon: ImageVector,
    val screenComposable: @Composable () -> Unit
) {
    MAP(R.string.main_bottom_nav_label_home, Icons.Rounded.Home, { MapScreen() }),
    ADD_PIN(R.string.main_bottom_nav_label_add, Icons.Rounded.AddCircle, { AddPinScreen() }),
    PINS_LIST(R.string.main_bottom_nav_label_list, Icons.Rounded.List, { PinsListScreen() }),
}

package com.arturborowy.pins.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.arturborowy.pins.R
import com.arturborowy.pins.Screen

enum class BottomNavItem(
    val route: String,
    @StringRes val textResId: Int,
    val icon: ImageVector,
) {
    HOME(Screen.MAIN.name, R.string.main_bottom_nav_label_home, Icons.Rounded.Home),
    ADD_PIN(Screen.ADD_PIN.name, R.string.main_bottom_nav_label_add, Icons.Rounded.AddCircle),
    PINS_LIST(Screen.PINS_LIST.name, R.string.main_bottom_nav_label_list, Icons.Rounded.List),
}

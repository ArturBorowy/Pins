package com.arturborowy.brand.designsystem.size

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Sizing(
    val bottomBarHeight: Dp = 65.dp,
    val icon: Dp = 24.dp,
    val progressBarSize: Dp = 64.dp,
    val strokeWidth: Dp = 1.dp,

    val shadow: Dp = 2.dp,
    val bottomBarShadow: Dp = 10.dp,
)

internal val LocalSizing = staticCompositionLocalOf { Sizing() }

package com.arturborowy.pins.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizing(
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 24.dp,
)

val LocalSizing = compositionLocalOf { Sizing() }

val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current
package com.arturborowy.pins.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun pxToDp(pixelSize: Int): Dp {
    return with(LocalDensity.current) { pixelSize.toDp() }
}
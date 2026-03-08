package com.arturborowy.pins.screen.map

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class MapMarkerItem(
    val label: String,
    @DrawableRes val iconResId: Int,
    val latitude: Double,
    val longitude: Double
)

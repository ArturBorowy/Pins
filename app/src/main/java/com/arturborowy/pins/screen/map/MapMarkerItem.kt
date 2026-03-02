package com.arturborowy.pins.screen.map

import androidx.annotation.DrawableRes

data class MapMarkerItem(
    val label: String,
    @DrawableRes val iconResId: Int,
    val latitude: Double,
    val longitude: Double
)

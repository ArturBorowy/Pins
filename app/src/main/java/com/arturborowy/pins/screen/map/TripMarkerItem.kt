package com.arturborowy.pins.screen.map

import androidx.annotation.DrawableRes

data class TripMarkerItem(
    val label: String,
    @DrawableRes val countryIconResId: Int,
    val latitude: Double,
    val longitude: Double
)

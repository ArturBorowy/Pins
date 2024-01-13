package com.arturborowy.pins.screen.map

import androidx.annotation.DrawableRes

data class TripMarker(
    val label: String,
    @DrawableRes val countryIconResId: Int,
    val latitude: Double,
    val longitude: Double
)

package com.arturborowy.pins.domain

import androidx.annotation.DrawableRes

data class Country(
    val countryId: String,
    val countryLabel: String,
    @DrawableRes val countryIcon: Int
)
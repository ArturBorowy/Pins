package com.arturborowy.pins.model.places

import androidx.annotation.DrawableRes

data class CountryEntity(
    val countryId: String,
    val countryLabel: String,
    @DrawableRes val countryIcon: Int
)
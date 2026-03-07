package com.arturborowy.pins.domain

import androidx.annotation.DrawableRes

data class Country(
    val countryId: Id,
    val countryLabel: String,
    @DrawableRes val countryIcon: Int
) {

    @JvmInline
    value class Id(val value: String)
}
package com.arturborowy.pins.data.remote.geocoding

import com.arturborowy.pins.domain.Country

data class CountryDto(
    val id: Country.Id,
    val label: String,
)

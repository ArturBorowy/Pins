package com.arturborowy.pins.domain

data class AddressPrediction(
    val id: Id,
    val label: String
) {
    @JvmInline
    value class Id(val value: String)
}

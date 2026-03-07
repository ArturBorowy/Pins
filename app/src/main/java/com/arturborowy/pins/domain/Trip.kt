package com.arturborowy.pins.domain

data class Trip(
    val id: Id,
    val name: String,
    val stops: List<StopDetails>
) {
    @JvmInline
    value class Id(val value: Long)
}

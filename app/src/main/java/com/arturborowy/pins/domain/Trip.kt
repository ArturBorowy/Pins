package com.arturborowy.pins.domain

data class Trip(
    val id: Long,
    val name: String,
    val stops: List<StopDetails>
)

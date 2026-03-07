package com.arturborowy.pins.domain

interface TripRepository {
    suspend fun getTrip(id: Trip.Id): Trip
    suspend fun getAllTrips(): List<Trip>
    suspend fun saveTrip(name: String, stops: List<StopDetails>)
    suspend fun updateTrip(trip: Trip)
    suspend fun removeTrip(id: Long)
}

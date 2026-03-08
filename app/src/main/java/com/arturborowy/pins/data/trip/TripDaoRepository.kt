package com.arturborowy.pins.data.trip

import com.arturborowy.pins.data.db.StopEntityDao
import com.arturborowy.pins.data.db.TripDao
import com.arturborowy.pins.domain.PlaceDetailsWithCountry
import com.arturborowy.pins.domain.StopDetails
import com.arturborowy.pins.domain.Trip
import com.arturborowy.pins.domain.TripRepository
import java.util.Calendar
import javax.inject.Inject

class TripDaoRepository @Inject constructor(
    private val tripDao: TripDao,
    private val stopEntityDao: StopEntityDao
) : TripRepository {

    override suspend fun getTrip(id: Trip.Id): Trip {
        val tripEntity = tripDao.select(id.value)
        val stops = stopEntityDao.select(tripEntity.id)
        return mapToDomainTrip(tripEntity, stops)
    }

    private fun mapToDomainTrip(tripEntity: TripEntity, stopEntities: List<StopEntity>): Trip {
        val stops = stopEntities.map { stopEntity ->
            StopDetails(
                date(stopEntity.arrivalDate)!!,
                date(stopEntity.departureDate),
                PlaceDetailsWithCountry(
                    stopEntity.locationName,
                    stopEntity.latitude,
                    stopEntity.longitude,
                    stopEntity.country
                )
            )
        }
        return Trip(Trip.Id(tripEntity.id), tripEntity.name, stops)
    }

    private fun date(timeInMillis: Long?) =
        timeInMillis?.let {
            Calendar.getInstance().apply { this.timeInMillis = timeInMillis }.time
        }

    override suspend fun getAllTrips(): List<Trip> =
        tripDao.select().map { tripEntity ->
            val stops = stopEntityDao.select(tripEntity.id)
            mapToDomainTrip(tripEntity, stops)
        }

    override suspend fun saveTrip(name: String, stops: List<StopDetails>) {
        val tripId = tripDao.insert(TripEntity(name))
        stops.forEach { stop ->
            stopEntityDao.insert(mapToStopEntity(stop, tripId))
        }
    }

    private fun mapToStopEntity(stopDetails: StopDetails, tripId: Long) = StopEntity(
        stopDetails.placeDetailsWithCountry.locationName,
        stopDetails.arrivalDate.time,
        stopDetails.departureDate?.time,
        stopDetails.placeDetailsWithCountry.latitude,
        stopDetails.placeDetailsWithCountry.longitude,
        stopDetails.placeDetailsWithCountry.country,
        tripId
    )

    override suspend fun updateTrip(trip: Trip) {
        tripDao.insert(TripEntity(trip.name, trip.id.value))
        trip.stops.forEach { stop ->
            stopEntityDao.insert(mapToStopEntity(stop, trip.id.value))
        }
    }

    override suspend fun removeTrip(id: Long) {
        tripDao.remove(id)
    }
}

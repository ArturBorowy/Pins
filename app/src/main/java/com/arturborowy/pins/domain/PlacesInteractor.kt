package com.arturborowy.pins.domain

import com.arturborowy.pins.data.com.arturborowy.pins.flags.CountryIconsRepository
import com.arturborowy.pins.data.db.StopEntityDao
import com.arturborowy.pins.data.db.TripDao
import com.arturborowy.pins.data.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.data.remote.places.PlacesPredictionRepository
import com.arturborowy.pins.data.trip.StopEntity
import com.arturborowy.pins.data.trip.TripEntity
import java.util.Calendar
import javax.inject.Inject

class PlacesInteractor @Inject constructor(
    private val tripDao: TripDao,
    private val stopEntityDao: StopEntityDao,
    private val placesPredictionRepository: PlacesPredictionRepository,
    private val geocodingRepository: GeocodingRepository,
    private val countryIconsRepository: CountryIconsRepository
) {

    suspend fun getAddressPredictions(inputString: String) =
        placesPredictionRepository.getAddressPredictions(inputString)
            .map { AddressPrediction(AddressPrediction.Id(it.id), it.label) }

    suspend fun getPlaceDetails(id: AddressPrediction.Id): PlaceDetails {
        val placeDetailsDto = placesPredictionRepository.getPlaceDetails(id)
        val country =
            getCountryOfGivenLatLong(placeDetailsDto.latitude, placeDetailsDto.longitude)

        return PlaceDetails(
            placeDetailsDto.locationName,
            placeDetailsDto.latitude,
            placeDetailsDto.longitude,
            country
        )
    }

    suspend fun getSingleStopTrip(tripId: Trip.Id): Trip {
        val tripEntity = tripDao.select(tripId.value)
        return getTripFromTripEntity(tripEntity)
    }

    suspend fun saveSingleStopTrip(
        tripName: String,
        stopDetails: StopDetails
    ) {
        val tripId = tripDao.insert(TripEntity(tripName))

        val stopEntity = StopEntity(
            stopDetails.placeDetails.locationName,
            stopDetails.arrivalDate.time,
            stopDetails.departureDate?.time,
            stopDetails.placeDetails.latitude,
            stopDetails.placeDetails.longitude,
            stopDetails.placeDetails.country,
            tripId
        )

        stopEntityDao.insert(stopEntity)
    }

    suspend fun updateTrip(trip: Trip) {
        tripDao.insert(TripEntity(trip.name, trip.id.value))

        trip.stops.forEach { stop ->
            stopEntityDao.insert(
                StopEntity(
                    stop.placeDetails.locationName,
                    stop.arrivalDate.time,
                    stop.departureDate?.time,
                    stop.placeDetails.latitude,
                    stop.placeDetails.longitude,
                    stop.placeDetails.country,
                    trip.id.value
                )
            )
        }
    }

    suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double): Country {
        val countryDto = geocodingRepository.getCountryOfGivenLatLong(latitude, longitude)
        val countryIcon = countryIconsRepository.getIcon(countryDto.id.value)

        return Country(countryDto.id, countryDto.label, countryIcon!!)
    }

    suspend fun getPlaces() =
        tripDao.select()
            .map { getTripFromTripEntity(it) }

    private suspend fun getTripFromTripEntity(tripEntity: TripEntity): Trip {
        val stops = stopEntityDao.select(tripEntity.id)
        return mapTripEntitiesToTrip(tripEntity, stops)
    }

    private fun mapTripEntitiesToTrip(
        tripEntity: TripEntity,
        stopEntities: List<StopEntity>
    ): Trip {
        val stops = stopEntities.map { stopEntity ->
            StopDetails(
                date(stopEntity.arrivalDate)!!,
                date(stopEntity.departureDate),
                PlaceDetails(
                    stopEntity.locationName,
                    stopEntity.latitude,
                    stopEntity.longitude,
                    stopEntity.country
                )
            )
        }

        return Trip(
            Trip.Id(tripEntity.id),
            tripEntity.name,
            stops
        )
    }

    private fun date(timeInMillis: Long?) =
        timeInMillis?.let {
            Calendar.getInstance()
                .apply { this.timeInMillis = timeInMillis }.time
        }

    suspend fun removePlaceDetails(id: Long) {
        tripDao.remove(id)
    }

    suspend fun saveMultiStopTrip(
        nameText: String,
        stops: List<StopDetails>
    ) {
        val tripId = tripDao.insert(TripEntity(nameText))

        stops.forEach { stop ->
            stopEntityDao.insert(
                StopEntity(
                    stop.placeDetails.locationName,
                    stop.arrivalDate.time,
                    stop.departureDate?.time,
                    stop.placeDetails.latitude,
                    stop.placeDetails.longitude,
                    stop.placeDetails.country,
                    tripId
                )
            )
        }
    }
}
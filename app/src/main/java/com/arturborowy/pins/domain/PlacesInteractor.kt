package com.arturborowy.pins.domain

import com.arturborowy.pins.model.countryicons.CountryIconsRepository
import com.arturborowy.pins.model.db.PlaceDetailsDao
import com.arturborowy.pins.model.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.model.remote.places.PlacesPredictionRepository
import com.arturborowy.pins.model.trip.TripSingleStopEntity
import java.util.Date
import javax.inject.Inject

class PlacesInteractor @Inject constructor(
    private val placeDetailsDao: PlaceDetailsDao,
    private val placesPredictionRepository: PlacesPredictionRepository,
    private val geocodingRepository: GeocodingRepository,
    private val countryIconsRepository: CountryIconsRepository
) {

    suspend fun getAddressPredictions(inputString: String) =
        placesPredictionRepository.getAddressPredictions(inputString)

    suspend fun getPlaceDetails(id: String): PlaceDetails {
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

    suspend fun saveSingleStopTrip(
        tripName: String,
        arrivalDate: Date,
        departureDate: Date,
        placeDetails: PlaceDetails
    ) {

        placeDetailsDao.insert(
            TripSingleStopEntity(
                tripName,
                placeDetails.locationName,
                arrivalDate.time,
                departureDate.time,
                placeDetails.latitude,
                placeDetails.longitude,
                placeDetails.country
            )
        )
    }

    suspend fun getCountryOfGivenLatLong(latitude: Double, longitude: Double): Country {
        val countryDto = geocodingRepository.getCountryOfGivenLatLong(latitude, longitude)
        val countryIcon = countryIconsRepository.getIcon(countryDto.id)

        return Country(countryDto.id, countryDto.label, countryIcon!!)
    }

    suspend fun getPlaces() = placeDetailsDao.select()

    suspend fun removePlaceDetails(id: String) {
        placeDetailsDao.removePlaceDetails(id)
    }
}
package com.arturborowy.pins.domain

import com.arturborowy.pins.flags.CountryIconsRepository
import com.arturborowy.pins.data.remote.geocoding.GeocodingRepository
import com.arturborowy.pins.data.remote.places.PlacesPredictionRepository
import javax.inject.Inject

class PlacesInteractor @Inject constructor(
    private val tripRepository: TripRepository,
    private val placesPredictionRepository: PlacesPredictionRepository,
    private val geocodingRepository: GeocodingRepository,
    private val countryIconsRepository: CountryIconsRepository,
) {

    suspend fun getAddressPredictions(inputString: String) =
        placesPredictionRepository.getAddressPredictions(inputString)

    suspend fun getPlaceDetails(id: AddressPrediction.Id): PlaceDetailsWithCountry {
        val placeDetailsDto = placesPredictionRepository.fetchPlaceDetailsDto(id)
        val countryDto = geocodingRepository.getCountryOfGivenLatLong(
            placeDetailsDto.latitude,
            placeDetailsDto.longitude
        )
        val countryIcon = countryIconsRepository.getIcon(countryDto.id.value)

        return PlaceDetailsWithCountry(
            placeDetailsDto.locationName,
            placeDetailsDto.latitude,
            placeDetailsDto.longitude,
            Country(countryDto.id, countryDto.label, countryIcon!!)
        )
    }

    suspend fun getSingleStopTrip(tripId: Trip.Id): Trip =
        tripRepository.getTrip(tripId)

    suspend fun saveSingleStopTrip(tripName: String, stopDetails: StopDetails) {
        tripRepository.saveTrip(tripName, listOf(stopDetails))
    }

    suspend fun updateTrip(trip: Trip) {
        tripRepository.updateTrip(trip)
    }

    suspend fun getPlaces() = tripRepository.getAllTrips()

    suspend fun removePlaceDetails(id: Trip.Id) {
        tripRepository.removeTrip(id.value)
    }

    suspend fun saveMultiStopTrip(nameText: String, stops: List<StopDetails>) {
        tripRepository.saveTrip(nameText, stops)
    }
}

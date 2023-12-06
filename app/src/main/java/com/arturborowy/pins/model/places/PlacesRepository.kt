package com.arturborowy.pins.model.places

interface PlacesRepository {

    suspend fun getAddressPredictions(inputString: String): List<AddressPrediction>

    suspend fun getPlaceDetails(id: String): PlaceDetails

    suspend fun savePlace(placeDetails: PlaceDetails)

    suspend fun getPlaces(): List<PlaceDetails>
}
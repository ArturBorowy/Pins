package com.arturborowy.pins.model.places

interface PlacesPredictionRepository {

    suspend fun getAddressPredictions(inputString: String): List<AddressPrediction>

    suspend fun getPlaceDetails(id: String): PlaceDetails
}
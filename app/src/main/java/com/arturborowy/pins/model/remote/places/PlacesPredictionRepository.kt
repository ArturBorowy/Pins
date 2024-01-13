package com.arturborowy.pins.model.remote.places

interface PlacesPredictionRepository {

    suspend fun getAddressPredictions(inputString: String): List<AddressPredictionDto>

    suspend fun getPlaceDetails(id: String): PlaceDetailsDto
}
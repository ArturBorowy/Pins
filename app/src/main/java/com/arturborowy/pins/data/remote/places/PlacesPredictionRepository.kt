package com.arturborowy.pins.data.remote.places

interface PlacesPredictionRepository {

    suspend fun getAddressPredictions(inputString: String): List<AddressPredictionDto>

    suspend fun getPlaceDetails(id: String): PlaceDetailsDto
}
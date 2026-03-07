package com.arturborowy.pins.data.remote.places

import com.arturborowy.pins.domain.AddressPrediction

interface PlacesPredictionRepository {

    suspend fun getAddressPredictions(inputString: String): List<AddressPredictionDto>

    suspend fun getPlaceDetails(id: AddressPrediction.Id): PlaceDetailsDto
}
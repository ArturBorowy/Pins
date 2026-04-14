package com.arturborowy.pins.data.remote.places

import com.arturborowy.pins.domain.AddressPrediction
import com.arturborowy.pins.domain.PlaceDetails

interface PlacesPredictionRepository {

    suspend fun getAddressPredictions(inputString: String): List<AddressPrediction>

    suspend fun fetchPlaceDetailsDto(id: AddressPrediction.Id): PlaceDetails
}

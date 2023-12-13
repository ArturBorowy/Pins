package com.arturborowy.pins.model.places

import com.arturborowy.pins.model.db.PlaceDetailsDao
import javax.inject.Inject

class PlacesInteractor @Inject constructor(
    private val placeDetailsDao: PlaceDetailsDao,
    private val placesPredictionRepository: PlacesPredictionRepository
) {

    suspend fun getAddressPredictions(inputString: String) =
        placesPredictionRepository.getAddressPredictions(inputString)

    suspend fun getPlaceDetails(id: String) =
        placesPredictionRepository.getPlaceDetails(id)

    suspend fun savePlace(placeDetails: PlaceDetails) {
        placeDetailsDao.insert(placeDetails)
    }

    suspend fun getPlaces() = placeDetailsDao.select()
}
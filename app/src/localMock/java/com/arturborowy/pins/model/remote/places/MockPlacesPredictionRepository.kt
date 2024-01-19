package com.arturborowy.pins.model.remote.places

import com.ultimatelogger.android.output.ALog
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MockPlacesPredictionRepository : PlacesPredictionRepository {

    override suspend fun getAddressPredictions(inputString: String) =
        suspendCoroutine {
            ALog.d("inputString: $inputString")

            if (inputString == EXPECTED_ADDRESS_PREDICTION_STRING) {
                val predictions = FETCHED_ADDRESS_PREDICTIONS

                ALog.d("result: $predictions")
                it.resume(predictions)
            } else {
                it.resumeWithException(MockInputException())
            }
        }

    override suspend fun getPlaceDetails(id: String) =
        suspendCoroutine {
            ALog.d("placeId: $id")

            if (id == EXPECTED_PLACE_ID_FOR_DETAILS) {
                val placeDetails = FETCHED_PLACE_DETAILS

                ALog.d("result: $placeDetails")
                it.resume(FETCHED_PLACE_DETAILS)
            } else {
                it.resumeWithException(MockInputException())
            }
        }

    companion object {
        const val EXPECTED_ADDRESS_PREDICTION_STRING = "krak"
        const val EXPECTED_PLACE_ID_FOR_DETAILS = "001"

        val FETCHED_PLACE_DETAILS = PlaceDetailsDto("Kraków", 50.0647, 19.9450)
        val FETCHED_ADDRESS_PREDICTIONS = listOf(
            AddressPredictionDto("001", "Kraków, Małopolskie, Poland"),
            AddressPredictionDto("002", "Krakówek, Mazowieckie, Poland"),
            AddressPredictionDto("003", "Krakówec, Bavaria, Germany"),
        )
    }
}
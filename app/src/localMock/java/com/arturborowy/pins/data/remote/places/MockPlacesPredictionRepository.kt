package com.arturborowy.pins.data.remote.places

import com.arturborowy.pins.domain.AddressPrediction
import com.arturborowy.pins.domain.PlaceDetails
import com.ultimatelogger.android.output.ALog
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MockPlacesPredictionRepository : PlacesPredictionRepository {

    override suspend fun getAddressPredictions(inputString: String) =
        suspendCancellableCoroutine {
            ALog.d("inputString: $inputString")

            when (inputString) {
                EXPECTED_ADDRESS_PREDICTION_STRING -> {
                    val predictions = FETCHED_ADDRESS_PREDICTIONS

                    ALog.d("result: $predictions")
                    it.resume(predictions)
                }

                ALTERNATIVE_EXPECTED_ADDRESS_PREDICTION_STRING -> {
                    val predictions = ALTERNATIVE_FETCHED_ADDRESS_PREDICTIONS

                    ALog.d("result: $predictions")
                    it.resume(predictions)
                }

                else -> it.resumeWithException(MockInputException())
            }
        }

    override suspend fun fetchPlaceDetailsDto(id: AddressPrediction.Id) =
        suspendCancellableCoroutine {
            ALog.d("placeId: $id")

            when (id) {
                EXPECTED_PLACE_ID_FOR_DETAILS -> {
                    val placeDetails = FETCHED_PLACE_DETAILS

                    ALog.d("result: $placeDetails")
                    it.resume(placeDetails)
                }

                ALTERNATIVE_EXPECTED_PLACE_ID_FOR_DETAILS -> {
                    val placeDetails = ALTERNATIVE_FETCHED_PLACE_DETAILS

                    ALog.d("result: $placeDetails")
                    it.resume(placeDetails)
                }

                else -> it.resumeWithException(MockInputException())
            }
        }

    companion object {
        const val EXPECTED_ADDRESS_PREDICTION_STRING = "krak"
        val EXPECTED_PLACE_ID_FOR_DETAILS = AddressPrediction.Id("001")

        val FETCHED_PLACE_DETAILS = PlaceDetails("Kraków", 50.0647, 19.9450)
        val FETCHED_ADDRESS_PREDICTIONS = listOf(
            AddressPrediction(AddressPrediction.Id("001"), "Kraków, Małopolskie, Poland"),
            AddressPrediction(AddressPrediction.Id("002"), "Krakówek, Mazowieckie, Poland"),
            AddressPrediction(AddressPrediction.Id("003"), "Krakówec, Bavaria, Germany"),
        )

        const val ALTERNATIVE_EXPECTED_ADDRESS_PREDICTION_STRING = "war"
        val ALTERNATIVE_EXPECTED_PLACE_ID_FOR_DETAILS = AddressPrediction.Id("004")

        val ALTERNATIVE_FETCHED_PLACE_DETAILS = PlaceDetails("Warszawa", 52.2297, 21.0122)
        val ALTERNATIVE_FETCHED_ADDRESS_PREDICTIONS = listOf(
            AddressPrediction(AddressPrediction.Id("004"), "Warszawa, Mazowieckie, Poland"),
        )
    }
}

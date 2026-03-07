package com.arturborowy.pins.model.remote.places

import com.arturborowy.pins.data.remote.places.AddressPredictionDto
import com.arturborowy.pins.data.remote.places.PlaceDetailsDto
import com.arturborowy.pins.data.remote.places.PlacesPredictionRepository
import com.arturborowy.pins.domain.AddressPrediction
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.ultimatelogger.android.output.ALog
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GooglePlacesClientRepository @Inject constructor(
    private val placesClient: PlacesClient,
) : PlacesPredictionRepository {

    override suspend fun getAddressPredictions(inputString: String) =
        getAutocompletePredictions(inputString)
            .map {
                AddressPredictionDto(
                    it.placeId,
                    it.getFullText(null).toString(),
                )
            }

    private suspend fun getAutocompletePredictions(inputString: String) =
        suspendCancellableCoroutine<List<AutocompletePrediction>> {
            ALog.d("inputString: $inputString")

            val request = buildAutocompletePredictionsRequest(inputString)
            ALog.d("request: $request")

            placesClient.findAutocompletePredictions(request)
                .addOnCompleteListener { completedTask ->
                    if (completedTask.exception == null) {
                        val result = completedTask.result.autocompletePredictions
                        ALog.d("result: $result")
                        it.resume(result)
                    } else {
                        ALog.e(completedTask.exception?.stackTraceToString().orEmpty())
                        it.resumeWithException(completedTask.exception!!)
                    }
                }
        }

    private fun buildAutocompletePredictionsRequest(inputString: String) =
        FindAutocompletePredictionsRequest.builder()
            .setTypesFilter(listOf(PlaceTypes.CITIES))
            .setSessionToken(AutocompleteSessionToken.newInstance())
            .setQuery(inputString)
            .build()

    override suspend fun getPlaceDetails(id: AddressPrediction.Id) =
        suspendCancellableCoroutine {
            ALog.d("placeId: $id")

            val placeFields = mutableListOf(
                Place.Field.DISPLAY_NAME,
                Place.Field.LOCATION
            )

            placesClient.fetchPlace(FetchPlaceRequest.newInstance(id.value, placeFields))
                .addOnCompleteListener { completedTask ->
                    if (completedTask.exception == null) {
                        val fetchedPlace = completedTask.result.place
                        ALog.d("Fetched place: $fetchedPlace")

                        val placeDetails = PlaceDetailsDto(
                            fetchedPlace.displayName!!,
                            fetchedPlace.location!!.latitude,
                            fetchedPlace.location!!.longitude,
                        )
                        ALog.d("result: $placeDetails")

                        it.resume(placeDetails)
                    } else {
                        ALog.e(completedTask.exception?.stackTraceToString().orEmpty())
                        it.resumeWithException(completedTask.exception!!)
                    }
                }
        }
}
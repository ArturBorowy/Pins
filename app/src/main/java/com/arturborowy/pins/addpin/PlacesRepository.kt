package com.arturborowy.pins.addpin

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.ultimatelogger.android.output.ALog
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PlacesRepository @Inject constructor(
    private val placesClient: PlacesClient
) {
    suspend fun getAddressPredictions(inputString: String) =
        _getAddressPredictions(inputString)
            .map {
                AddressPrediction(
                    it.placeId,
                    it.getPrimaryText(null).toString(),
                )
            }

    private suspend fun _getAddressPredictions(inputString: String) =
        suspendCoroutine<List<AutocompletePrediction>> {
            placesClient.findAutocompletePredictions(
                FindAutocompletePredictionsRequest.builder()
                    .setTypesFilter(listOf(PlaceTypes.ADDRESS))
                    .setSessionToken(AutocompleteSessionToken.newInstance())
                    .setQuery(inputString)
                    .build()
            ).addOnCompleteListener { completedTask ->
                if (completedTask.exception == null) {
                    it.resume(completedTask.result.autocompletePredictions)
                } else {
                    ALog.e(completedTask.exception?.stackTraceToString().orEmpty())
                    it.resume(listOf())
                }
            }
        }

    suspend fun getPlaceDetails(id: String) =
        suspendCoroutine {
            val placeFields = mutableListOf(
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )

            placesClient.fetchPlace(FetchPlaceRequest.newInstance(id, placeFields))
                .addOnCompleteListener { completedTask ->
                    if (completedTask.exception == null) {
                        val fetchedPlace = completedTask.result.place
                        val placeAddress = PlaceAddress(
                            id,
                            fetchedPlace.latLng!!.latitude,
                            fetchedPlace.latLng!!.longitude,
                            fetchedPlace.name!!
                        )
                        it.resume(placeAddress)
                    } else {
                        ALog.e(completedTask.exception?.stackTraceToString().orEmpty())
                        it.resume(TODO())
                    }
                }
        }
}
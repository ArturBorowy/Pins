package com.arturborowy.pins.addpin

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
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
}
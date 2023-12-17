package com.arturborowy.pins.model.places

import android.location.Geocoder
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

class GooglePlacesClientRepository @Inject constructor(
    private val geocoder: Geocoder,
    private val placesClient: PlacesClient
) : PlacesPredictionRepository {

    override suspend fun getAddressPredictions(inputString: String) =
        getAutocompletePredictions(inputString)
            .map {
                AddressPrediction(
                    it.placeId,
                    it.getPrimaryText(null).toString(),
                )
            }

    private suspend fun getAutocompletePredictions(inputString: String) =
        suspendCoroutine<List<AutocompletePrediction>> {
            placesClient.findAutocompletePredictions(
                FindAutocompletePredictionsRequest.builder()
                    .setTypesFilter(listOf(PlaceTypes.CITIES))
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

    override suspend fun getPlaceDetails(id: String) =
        suspendCoroutine {
            val placeFields = mutableListOf(
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )

            placesClient.fetchPlace(FetchPlaceRequest.newInstance(id, placeFields))
                .addOnCompleteListener { completedTask ->
                    if (completedTask.exception == null) {
                        val fetchedPlace = completedTask.result.place

                        val address = getPlaceAddress(fetchedPlace.name!!)

                        val placeDetails = PlaceDetails(
                            id,
                            fetchedPlace.latLng!!.latitude,
                            fetchedPlace.latLng!!.longitude,
                            fetchedPlace.name!!,
                            CountryEntity(
                                address!!.countryCode,
                                address.countryName
                            )
                        )
                        it.resume(placeDetails)
                    } else {
                        ALog.e(completedTask.exception?.stackTraceToString().orEmpty())
                        it.resume(TODO())
                    }
                }
        }

    private fun getPlaceAddress(locationName: String) =
        geocoder.getFromLocationName(locationName, 1)
            ?.firstOrNull()
}
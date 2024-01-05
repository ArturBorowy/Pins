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
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GooglePlacesClientRepository @Inject constructor(
    private val geocoder: Geocoder,
    private val placesClient: PlacesClient,
    private val countryIconsRepository: CountryIconsRepository
) : PlacesPredictionRepository {

    override suspend fun getAddressPredictions(inputString: String) =
        getAutocompletePredictions(inputString)
            .map {
                AddressPrediction(
                    it.placeId,
                    it.getFullText(null).toString(),
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
            ALog.d("getPlaceDetails placeId: $id")

            val placeFields = mutableListOf(
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )

            placesClient.fetchPlace(FetchPlaceRequest.newInstance(id, placeFields))
                .addOnCompleteListener { completedTask ->
                    try {
                        if (completedTask.exception == null) {
                            val fetchedPlace = completedTask.result.place
                            ALog.d("Fetched place: $fetchedPlace")

                            val address = getPlaceAddress(fetchedPlace.name!!)
                            ALog.d("Fetched address: $address")

                            val placeDetails = PlaceDetails(
                                id,
                                fetchedPlace.latLng!!.latitude,
                                fetchedPlace.latLng!!.longitude,
                                fetchedPlace.name!!,
                                CountryEntity(
                                    address!!.countryCode,
                                    address.countryName,
                                    countryIconsRepository.getIcon(address.countryCode)!!
                                )
                            )
                            it.resume(placeDetails)
                        } else {
                            ALog.e(completedTask.exception?.stackTraceToString().orEmpty())
                            it.resumeWithException(completedTask.exception!!)
                        }
                    } catch (e: Exception) {
                        it.resumeWithException(e)
                    }
                }
        }

    private fun getPlaceAddress(locationName: String) =
        geocoder.getFromLocationName(locationName, 1)
            ?.firstOrNull()
}
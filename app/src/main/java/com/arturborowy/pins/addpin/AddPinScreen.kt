package com.arturborowy.pins.addpin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.arturborowy.pins.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPinScreen() {
    val scope = rememberCoroutineScope()

    Places.initialize(LocalContext.current, stringResource(R.string.maps_api_key))
    val placesClient = Places.createClient(LocalContext.current)

    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        TextField(
            value = text,
            label = { Text("TODO") },
            onValueChange = { value ->
                text = value
                scope.launch {
                    getAddressPredictions(placesClient, inputString = value)
                        .forEach {
                            Log.e("PLACES", it.toString())
                        }
                }
            })
    }
}

suspend fun getAddressPredictions(
    placesClient: PlacesClient,
    sessionToken: AutocompleteSessionToken = AutocompleteSessionToken.newInstance(),
    inputString: String,
    location: LatLng? = null
) = suspendCoroutine<List<AutocompletePrediction>> {

    placesClient.findAutocompletePredictions(
        FindAutocompletePredictionsRequest.builder()
            .setOrigin(location)
            .setCountries("US")
            .setTypesFilter(listOf(PlaceTypes.ADDRESS))
            .setSessionToken(sessionToken)
            .setQuery(inputString)
            .build()
    ).addOnCompleteListener { completedTask ->
        if (completedTask.exception == null) {
            it.resume(completedTask.result.autocompletePredictions)
        } else {
            Log.e("PLACES", completedTask.exception?.stackTraceToString().orEmpty())
            it.resume(listOf())
        }
    }
}
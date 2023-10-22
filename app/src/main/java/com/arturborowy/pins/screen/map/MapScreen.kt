package com.arturborowy.pins.screen.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapScreen(mapViewModel: MapViewModel = hiltViewModel()) {
    mapViewModel.onViewResume()

    val state = mapViewModel.state.collectAsState()

    val addressDetails = state.value.placeDetails

    GoogleMap {
        addressDetails.forEach {
            Marker(
                state = MarkerState(LatLng(it.latitude, it.longitude)),
                title = it.label,
            )
        }
    }
}
package com.arturborowy.pins.screen.map

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.arturborowy.pins.utils.mapIconBitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
internal fun SelectedPlaceMap(state: MapViewModel.State, context: Context) {
    val location = LatLng(state.placeLatitude!!, state.placeLongitude!!)
    GoogleMap(
        cameraPositionState = CameraPositionState(CameraPosition.fromLatLngZoom(location, 10f)),
        properties = mapProperties(context)
    ) {
        Marker(
            icon = mapIconBitmapDescriptor(LocalContext.current, state.placeCountryIcon!!),
            state = MarkerState(location),
            title = state.placeText
        )
    }
}
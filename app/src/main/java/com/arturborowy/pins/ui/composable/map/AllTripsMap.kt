package com.arturborowy.pins.ui.composable.map

import androidx.compose.runtime.Composable
import com.arturborowy.pins.screen.map.MapMarkerItem
import com.google.android.gms.maps.model.LatLng

private var portoPortugalLatLng = LatLng(41.1579, -8.6291)

@Composable
fun AllTripsMap(markerLists: List<List<MapMarkerItem>>) {
    val randomMarker = if (markerLists.isEmpty()) {
        null
    } else {
        markerLists.randomOrNull()?.randomOrNull()
    }

    val cameraLatitude = randomMarker?.latitude ?: portoPortugalLatLng.latitude
    val cameraLongitude = randomMarker?.longitude ?: portoPortugalLatLng.longitude

    Map(markerLists, 9f, cameraLatitude, cameraLongitude)
}


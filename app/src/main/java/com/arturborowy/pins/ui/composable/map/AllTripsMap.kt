package com.arturborowy.pins.ui.composable.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.arturborowy.pins.screen.map.MapMarkerItem
import com.google.android.gms.maps.model.LatLng

private const val PORTO_PORTUGAL_LONGITUDE = -8.6291
private const val PORTO_PORTUGAL_LATITUDE = 41.1579
private val portoPortugalLatLng = LatLng(PORTO_PORTUGAL_LATITUDE, PORTO_PORTUGAL_LONGITUDE)

@Composable
fun AllTripsMap(markerLists: List<List<MapMarkerItem>>) {
    var randomListIndex by rememberSaveable { mutableIntStateOf(-1) }
    var randomItemIndex by rememberSaveable { mutableIntStateOf(-1) }

    if (randomListIndex >= markerLists.size ||
        (randomListIndex >= 0 && randomItemIndex >= (markerLists.getOrNull(randomListIndex)?.size
            ?: 0))
    ) {
        randomListIndex = -1
        randomItemIndex = -1
    }

    if (randomListIndex < 0 && markerLists.isNotEmpty()) {
        randomListIndex = markerLists.indices.random()
        randomItemIndex = markerLists[randomListIndex].indices.randomOrNull() ?: -1
    }

    val randomMarker = if (randomListIndex >= 0 && randomItemIndex >= 0) {
        markerLists[randomListIndex][randomItemIndex]
    } else {
        null
    }

    val cameraLatitude = randomMarker?.latitude ?: portoPortugalLatLng.latitude
    val cameraLongitude = randomMarker?.longitude ?: portoPortugalLatLng.longitude

    Map(markerLists, 9f, cameraLatitude, cameraLongitude)
}


package com.arturborowy.pins.ui.composable.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import com.arturborowy.pins.screen.map.MapMarkerItem

@Composable
fun Map(
    markerLists: List<List<MapMarkerItem>>,
    zoom: Float,
    cameraLatitude: Double,
    cameraLongitude: Double,
    modifier: Modifier = Modifier,
) {
    if (LocalUseMockMap.current || LocalInspectionMode.current) {
        MockGoogleMap(
            markerLists = markerLists,
            zoom = zoom,
            cameraLatitude = cameraLatitude,
            cameraLongitude = cameraLongitude,
            modifier = modifier
        )
    } else {
        RealGoogleMap(
            markerLists = markerLists,
            zoom = zoom,
            cameraLatitude = cameraLatitude,
            cameraLongitude = cameraLongitude,
            modifier = modifier
        )
    }
}

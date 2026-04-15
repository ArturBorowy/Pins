package com.arturborowy.pins.ui.composable.map

import androidx.compose.runtime.Composable
import com.arturborowy.pins.screen.map.MapMarkerItem

private const val START_ZOOM_LEVEL = 10f

@Composable
fun SelectedPlaceMap(marker: MapMarkerItem) {
    Map(
        listOf(listOf(marker)),
        START_ZOOM_LEVEL,
        marker.latitude,
        marker.longitude
    )
}

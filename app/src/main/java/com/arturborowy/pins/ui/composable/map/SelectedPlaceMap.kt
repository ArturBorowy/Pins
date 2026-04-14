package com.arturborowy.pins.ui.composable.map

import androidx.compose.runtime.Composable
import com.arturborowy.pins.screen.map.MapMarkerItem

@Composable
fun SelectedPlaceMap(marker: MapMarkerItem) {
    Map(
        listOf(listOf(marker)),
        10f,
        marker.latitude,
        marker.longitude
    )
}

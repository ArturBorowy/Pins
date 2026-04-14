package com.arturborowy.pins.ui.composable.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import com.arturborowy.pins.screen.map.MapMarkerItem
import com.arturborowy.pins.utils.mapIconBitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapMarker(marker: MapMarkerItem) {
    val context = LocalContext.current

    Marker(
        anchor = Offset(0.5f, 0.5f),
        icon = mapIconBitmapDescriptor(context, marker.iconResId),
        state = MarkerState(LatLng(marker.latitude, marker.longitude)),
        title = marker.label,
    )
}

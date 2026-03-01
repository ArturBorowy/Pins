package com.arturborowy.pins.screen.map

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.utils.mapIconBitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline

@Composable
internal fun TripsMap(state: MapViewModel.State, context: Context) {
    //todo maybe on start just show some random pin on camera, but less zoom when setting?
    GoogleMap(properties = mapProperties(context)) {
        state.tripMarkers.forEach { tripMarkers ->
            if (tripMarkers.size == 1) {
                TripMarker(tripMarkers[0])
            } else {
                tripMarkers.forEachIndexed { index, tripMarker ->
                    TripMarker(tripMarker)

                    if (tripMarkers.size > index + 1) {
                        val nextTripMarker = tripMarkers[index + 1]

                        Polyline(
                            width = 7f,
                            points = listOf(
                                LatLng(tripMarker.latitude, tripMarker.longitude),
                                LatLng(nextTripMarker.latitude, nextTripMarker.longitude)
                            ),
                            color = PinsTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TripMarker(tripMarkerItem: TripMarkerItem) {
    Marker(
        anchor = Offset(0.5f, 0.5f),
        icon = mapIconBitmapDescriptor(LocalContext.current, tripMarkerItem.countryIconResId),
        state = MarkerState(LatLng(tripMarkerItem.latitude, tripMarkerItem.longitude)),
        title = tripMarkerItem.label,
    )
}

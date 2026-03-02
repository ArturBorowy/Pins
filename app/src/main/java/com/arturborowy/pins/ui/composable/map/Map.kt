package com.arturborowy.pins.ui.composable.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.arturborowy.pins.screen.map.MapMarkerItem
import com.arturborowy.pins.ui.theme.PinsTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline

@Composable
fun Map(
    markerLists: List<List<MapMarkerItem>>,
    zoom: Float,
    cameraLatitude: Double,
    cameraLongitude: Double,
) {
    val context = LocalContext.current

    val cameraLatLng = LatLng(cameraLatitude, cameraLongitude)
    val cameraPosition = CameraPositionState(CameraPosition.fromLatLngZoom(cameraLatLng, zoom))

    GoogleMap(
        cameraPositionState = cameraPosition,
        properties = themedMapProperties(context)
    ) {
        markerLists.forEach { trip ->
            trip.forEachIndexed { index, marker ->
                MapMarker(marker)

                if (trip.size > index + 1) {
                    val nextMarker = trip[index + 1]

                    Polyline(
                        width = 7f,
                        points = listOf(
                            LatLng(marker.latitude, marker.longitude),
                            LatLng(nextMarker.latitude, nextMarker.longitude)
                        ),
                        color = PinsTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

//Can't render preview with GoogleMap @Composable
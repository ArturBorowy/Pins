package com.arturborowy.pins.ui.composable.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.screen.map.MapMarkerItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Map(
    markerLists: List<List<MapMarkerItem>>,
    zoom: Float,
    cameraLatitude: Double,
    cameraLongitude: Double,
) {
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(cameraLatitude, cameraLongitude), zoom)
    }

    LaunchedEffect(cameraLatitude, cameraLongitude) {
        cameraPosition.move(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    cameraLatitude,
                    cameraLongitude
                ), zoom
            )
        )
    }

    GoogleMap(
        cameraPositionState = cameraPosition,
        properties = themedMapProperties()
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
                        color = BrandTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

//Can't render preview with GoogleMap @Composable

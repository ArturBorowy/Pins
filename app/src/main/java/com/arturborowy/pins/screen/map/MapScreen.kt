package com.arturborowy.pins.screen.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.utils.bitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapScreen(mapViewModel: MapViewModel = hiltViewModel()) {
    mapViewModel.onViewResume()

    val state = mapViewModel.state.collectAsState()

    val addressDetails = state.value.placeDetails

    Box {
        GoogleMap {
            addressDetails.forEach {
                Marker(
                    icon = bitmapDescriptor(LocalContext.current, it.country.countryIcon),
                    state = MarkerState(LatLng(it.latitude, it.longitude)),
                    title = it.label,
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = Color.White,
            onClick = { mapViewModel.onAddPinClick() },
            content = {
                Icon(
                    modifier = Modifier.padding(12.dp),
                    painter = painterResource(R.drawable.ic_add_pin),
                    tint = colorResource(R.color.primary),
                    contentDescription = stringResource(R.string.main_bottom_nav_label_add)
                )
            })
    }
}
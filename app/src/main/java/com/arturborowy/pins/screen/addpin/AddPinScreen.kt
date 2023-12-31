package com.arturborowy.pins.screen.addpin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.utils.bitmapDescriptor
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPinScreen(
    viewModel: AddPinViewModel = hiltViewModel(),
    placeId: String? = null
) {
    viewModel.initPlaceId = placeId
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    var description by remember { mutableStateOf("") }

    val (state, setState) = viewModel.state.collectAsMutableState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.teal_700))
            .wrapContentSize(Alignment.TopStart)
    ) {
        Box {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.placeText,
                singleLine = true,
                onValueChange = {
                    setState(
                        state.copy(
                            placeText = it,
                            placeTextChangedByUser = true
                        )
                    )
                },
                label = { Text(stringResource(R.string.add_pin_hint_name)) },
            )
            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = state.expandAddressPredictions && state.placeTextChangedByUser,
                properties = PopupProperties(
                    clippingEnabled = false,
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                onDismissRequest = {}
            ) {
                state.predictions.forEach { addressPrediction ->
                    DropdownMenuItem(
                        onClick = { viewModel.onAddressSelect(addressPrediction.id) },
                        text = { Text(addressPrediction.label) }
                    )
                }
            }
        }
        if (state.areExtraFieldsVisible) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = {
                    viewModel.onDescriptionTextChange(it)
                    description = it
                },
                label = { Text(stringResource(R.string.add_pin_hint_description)) },
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onSaveAddress() }
            ) {
                Text(text = stringResource(R.string.add_pin_btn_confirm))
            }
        }

        if (state.showRemoveBtn) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onRemovePinClick() }
            ) {
                Text(text = stringResource(R.string.add_pin_btn_remove))
            }
        }

        if (state.placeLongitude != null
            && state.placeLatitude != null
            && state.placeCountryIcon != null
        ) {
            val location = LatLng(state.placeLatitude, state.placeLongitude)
            val cameraPositionState =
                CameraPositionState(CameraPosition.fromLatLngZoom(location, 10f))
            GoogleMap(cameraPositionState = cameraPositionState) {
                Marker(
                    icon = bitmapDescriptor(LocalContext.current, state.placeCountryIcon),
                    state = MarkerState(location),
                    title = state.placeText
                )
            }
        }
    }
}
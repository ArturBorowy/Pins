package com.arturborowy.pins.screen.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.Fab
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
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    Box {
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
        } else {
            GoogleMap {
                state.placeDetails.forEach {
                    Marker(
                        icon = bitmapDescriptor(LocalContext.current, it.country.countryIcon),
                        state = MarkerState(LatLng(it.latitude, it.longitude)),
                        title = it.label,
                    )
                }
            }
        }

        if (state.showAddressTextField) {
            Column(modifier = Modifier.padding(8.dp)) {
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        textColor = Color.Black,
                        placeholderColor = colorResource(R.color.primary),
                        cursorColor = Color.Black,
                        selectionColors = TextSelectionColors(
                            colorResource(R.color.primary),
                            colorResource(R.color.primary)
                        ),
                        focusedIndicatorColor = colorResource(R.color.primary),
                        unfocusedIndicatorColor = colorResource(R.color.primary),
                        focusedLabelColor = colorResource(R.color.primary),
                    ),
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
                    leadingIcon = {
                        TextButton(onClick = { viewModel.onBackEditingAddress() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_back_editing),
                                tint = colorResource(R.color.primary),
                                contentDescription = stringResource(R.string.add_pin_cd_address_editing_back)
                            )
                        }
                    },
                    trailingIcon = {
                        if (state.areExtraFieldsVisible) {
                            TextButton(onClick = { viewModel.onSaveAddress() }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_done),
                                    tint = colorResource(R.color.primary),
                                    contentDescription = stringResource(R.string.add_pin_btn_confirm)
                                )
                            }
                        }
                    })
                DropdownMenu(
                    modifier = Modifier.background(Color.White),
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
                            text = {
                                Text(
                                    addressPrediction.label,
                                    color = Color.Black,
                                )
                            }
                        )
                    }
                }
            }
        } else {
            Fab(
                R.drawable.ic_add_pin,
                R.string.main_bottom_nav_label_add,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            ) { viewModel.onAddPinClick() }
        }
    }
}
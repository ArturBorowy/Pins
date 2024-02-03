package com.arturborowy.pins.screen.map

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.CircularProgressBar
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.utils.addBorderToCircle
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.cropBitmapToCircle
import com.arturborowy.pins.utils.getBitmapFromVectorDrawable
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.showShortToast
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    if (state.errorText != null) {
        showShortToast(LocalContext.current, state.errorText)
        setState(state.copy(errorText = null))
    }

    val keyboard = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary))
    ) {
        CircularProgressBar(modifier = Modifier.align(Alignment.Center))

        if (state.placeLongitude != null && state.placeLatitude != null && state.placeCountryIcon != null) {
            val location = LatLng(state.placeLatitude, state.placeLongitude)

            val cameraPositionState =
                CameraPositionState(CameraPosition.fromLatLngZoom(location, 10f))

            GoogleMap(cameraPositionState = cameraPositionState) {
                Marker(
                    icon = mapIconBitmapDescriptor(LocalContext.current, state.placeCountryIcon),
                    state = MarkerState(location),
                    title = state.placeText
                )
            }
        } else {

            //todo maybe on start just show some random pin on camera, but less zoom when setting?
            GoogleMap {
                state.tripMarkers.forEach {
                    Marker(
                        icon = mapIconBitmapDescriptor(LocalContext.current, it.countryIconResId),
                        state = MarkerState(LatLng(it.latitude, it.longitude)),
                        title = it.label,
                    )
                }
            }
        }

        if (state.showAddressTextField) {
            SingleTripAddCard(
                placeText = state.placeText,
                onSearchTextChange = {
                    setState(
                        state.copy(
                            placeText = it, placeTextChangedByUser = true
                        )
                    )
                },
                onBackClick = { viewModel.onBackEditingAddress() },
                onConfirmClick = { viewModel.onConfirmAddress() },
                showConfirm = state.showConfirmAddressButton,
                expandDropdown = state.expandAddressPredictions && state.placeTextChangedByUser,
                showExtraEditionFields = state.showExtraFields,
                predictions = state.predictions,
                onAddressPredictionClick = { viewModel.onAddressSelect(it.id) },
                nameText = state.nameText,
                onNameTextChange = { viewModel.onTripNameChange(it) },
                arrivalDate = state.arrivalDate,
                onArrivalDateChange = { year: Int, month: Int, dayOfMonth: Int ->
                    viewModel.onArrivalDateChange(year, month, dayOfMonth)
                },
                departureDate = state.departureDate,
                onDepartureDateChange = { year: Int, month: Int, dayOfMonth: Int ->
                    viewModel.onDepartureDateChange(year, month, dayOfMonth)
                },
                onTripConfirmClick = { viewModel.onTripConfirmClick() },
                onTripCancelClick = { viewModel.onTripCancelClick() },
                keyboard = keyboard,
                isSavingEnabled = state.isSavingTripEnabled,
                isAddressEditEnabled = state.isAddressEditEnabled

            )
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

@Composable
fun mapIconBitmapDescriptor(
    context: Context,
    @DrawableRes vectorResId: Int
): BitmapDescriptor {
    val bitmap = getBitmapFromVectorDrawable(context, vectorResId, 0.05f)
        .cropBitmapToCircle()
        .addBorderToCircle(5.dp.value, context.getColor(R.color.primary))
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

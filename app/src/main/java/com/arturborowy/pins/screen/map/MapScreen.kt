package com.arturborowy.pins.screen.map

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PrimaryColorCircularProgressIndicator
import com.arturborowy.pins.ui.composable.TripAddCard
import com.arturborowy.pins.ui.theme.PinsTheme
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
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel = mapViewModel(false)) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    if (state.errorText != null) {
        showShortToast(LocalContext.current, state.errorText)
        setState(state.copy(errorText = null))
    }

    val keyboard = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
    ) {
        PrimaryColorCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        if (state.placeLongitude != null && state.placeLatitude != null && state.placeCountryIcon != null) {
            val location = LatLng(state.placeLatitude, state.placeLongitude)

            val cameraPositionState =
                CameraPositionState(CameraPosition.fromLatLngZoom(location, 10f))

            GoogleMap(
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                        context,
                        if (isSystemInDarkTheme()) {
                            R.raw.map_style_options_dark
                        } else {
                            R.raw.map_style_options_light
                        }
                    )
                )
            ) {
                Marker(
                    icon = mapIconBitmapDescriptor(LocalContext.current, state.placeCountryIcon),
                    state = MarkerState(location),
                    title = state.placeText
                )
            }
        } else {

            //todo maybe on start just show some random pin on camera, but less zoom when setting?
            GoogleMap(
                properties = MapProperties(
                    mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                        context,
                        if (isSystemInDarkTheme()) {
                            R.raw.map_style_options_dark
                        } else {
                            R.raw.map_style_options_light
                        }
                    )
                )
            ) {
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

        if (state.showAddressTextField) {
            TripAddCard(
                modifier = Modifier
                    .padding(8.dp),
                placeText = state.placeText,
                placeErrorText = state.placeErrorText,
                onSearchTextChange = {
                    setState(
                        state.copy(
                            placeText = it, placeTextChangedByUser = true
                        )
                    )
                },
                onBackClick = { viewModel.onBackEditingAddress() },
                showBackArrow = true,
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
                onPositiveClick = { viewModel.onTripConfirmClick() },
                positiveClickText = stringResource(R.string.create_trip_btn_confirm),
                onNegativeClick = { viewModel.onTripCancelClick() },
                negativeClickText = stringResource(R.string.create_trip_btn_cancel),
                onMiddleClick = { viewModel.onAddNextStopClick() },
                middleClickText = stringResource(R.string.create_trip_btn_next_stop),
                keyboard = keyboard,
                isSavingEnabled = state.isSavingTripEnabled,
                isAddressEditEnabled = state.isAddressEditEnabled,
                multiStop = state.multiStop
            )
        } else if (state.showAddPinButton) {
            Fab(
                R.drawable.ic_add_trip,
                R.string.main_bottom_nav_label_add,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            ) { viewModel.onAddTripClick() }
        } else if (state.showTripTypeBar) {
            AddTripTypesBar(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                { viewModel.onAddSingleStopTripClick() },
                { viewModel.onAddMultiStopTripClick() })
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

@Composable
fun AddTripTypesBar(
    modifier: Modifier, onSingleStopTripClick: () -> Unit, onMultipleStopTripClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(999.dp),
        colors = CardDefaults.cardColors(containerColor = PinsTheme.colorScheme.surface),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row {
            TextButton(
                onClick = { onSingleStopTripClick() },
                shape = RoundedCornerShape(999.dp, 0.dp, 0.dp, 999.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_trip_btn_single_stop),
                    color = PinsTheme.colorScheme.onSurface,
                    style = PinsTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(4.dp, 4.dp, 0.dp, 4.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Divider(
                modifier = Modifier
                    .padding(0.dp, 6.dp)
                    .width(2.dp)
                    .height(30.dp)
                    .align(Alignment.CenterVertically),
                color = PinsTheme.colorScheme.primary
            )

            TextButton(
                onClick = { onMultipleStopTripClick() },
                shape = RoundedCornerShape(0.dp, 999.dp, 999.dp, 0.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_trip_btn_multi_stop),
                    color = PinsTheme.colorScheme.onSurface,
                    style = PinsTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(0.dp, 4.dp, 4.dp, 4.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun mapIconBitmapDescriptor(
    context: Context, @DrawableRes vectorResId: Int
): BitmapDescriptor {
    val bitmap = getBitmapFromVectorDrawable(context, vectorResId, 0.05f).cropBitmapToCircle()
        .addBorderToCircle(5.dp.value, PinsTheme.colorScheme.primary.toArgb())
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
package com.arturborowy.pins.screen.edittrip

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.ui.composable.tripcard.TripAddCard
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.sizing
import com.arturborowy.pins.ui.theme.spacing
import com.arturborowy.pins.utils.mapIconBitmapDescriptor
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.showShortToast
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditTripScreen(viewModel: EditTripViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { showShortToast(context, it) }
    }

    val pagerState = rememberPagerState { state.stops.size }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                viewModel.onPageChanged(page)
            }
    }
    LaunchedEffect(state.currentStopId) {
        pagerState.scrollToPage(state.currentStopId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.alpha(if (state.isPreviousStopAvailable) 1f else 0f),
                onClick = { viewModel.onPreviousStopClick() }) {
                Icon(
                    modifier = Modifier
                        .size(PinsTheme.sizing.iconMedium),
                    painter = painterResource(R.drawable.ic_arrow_back),
                    tint = PinsTheme.colorScheme.onBackground,
                    contentDescription = stringResource(R.string.edit_trip_cd_previous_stop)
                )
            }
            PageTitle(
                text = stringResource(R.string.edit_trip_header),
            )

            IconButton(
                modifier = Modifier.alpha(if (state.isNextStopAvailable) 1f else 0f),
                onClick = { viewModel.onNextStopClick() }) {
                Icon(
                    modifier = Modifier
                        .size(PinsTheme.sizing.iconMedium),
                    painter = painterResource(R.drawable.ic_arrow_forward),
                    tint = PinsTheme.colorScheme.onBackground,
                    contentDescription = stringResource(R.string.edit_trip_cd_next_stop)
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            val stop = state.stops.getOrNull(page)

            stop?.let {
                EditStop(
                    viewModel,
                    state,
                    stop
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditStop(
    viewModel: EditTripViewModel,
    state: EditTripViewModel.State,
    stop: EditTripStopItem
) {
    val keyboard = LocalSoftwareKeyboardController.current

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PinsTheme.spacing.medium)
    ) {
        TripAddCard(
            placeText = stop.locationName,
            placeErrorText = state.placeErrorText,
            onSearchTextChange = { viewModel.onSearchTextChange(placeText = it) },
            onBackClick = { viewModel.onBackEditingAddress() },
            showBackArrow = true,
            onConfirmClick = { viewModel.onConfirmAddress() },
            showConfirm = state.showConfirmAddressButton,
            expandDropdown = state.expandAddressPredictions && state.placeTextChangedByUser,
            showExtraEditionFields = state.showExtraFields,
            predictions = state.predictions,
            onAddressPredictionClick = { viewModel.onAddressSelect(it.id) },
            nameText = state.tripName,
            onNameTextChange = { viewModel.onTripNameChange(it) },
            arrivalDate = stop.arrivalDateStr,
            onArrivalDateChange = { year: Int, month: Int, dayOfMonth: Int ->
                viewModel.onArrivalDateChange(year, month, dayOfMonth)
            },
            departureDate = stop.departureDateStr,
            onDepartureDateChange = { year: Int, month: Int, dayOfMonth: Int ->
                viewModel.onDepartureDateChange(year, month, dayOfMonth)
            },
            onPositiveClick = { viewModel.onSaveChangesClick() },
            positiveClickText = stringResource(R.string.edit_trip_btn_save_changes),
            onNegativeClick = { viewModel.onTripRemoveClick() },
            negativeClickText = stringResource(R.string.edit_trip_btn_delete),
            onMiddleClick = null,
            middleClickText = null,
            keyboard = keyboard,
            isSavingEnabled = state.isSavingTripEnabled,
            isAddressEditEnabled = state.isAddressEditEnabled,
            multiStop = state.stops.size > 1
        )
        WideCard(padding = PaddingValues(0.dp)) {
            val location = LatLng(stop.latitude, stop.longitude)

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
                    icon = mapIconBitmapDescriptor(
                        LocalContext.current,
                        stop.country.countryIcon
                    ),
                    state = MarkerState(location),
                    title = stop.locationName
                )
            }
        }
    }
}
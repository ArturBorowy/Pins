package com.arturborowy.pins.screen.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PrimaryColorCircularProgressIndicator
import com.arturborowy.pins.ui.composable.map.AllTripsMap
import com.arturborowy.pins.ui.composable.map.SelectedPlaceMap
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.showShortToast

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel = mapViewModel(false)) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { showShortToast(context, it) }
    }

    val keyboard = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
    ) {
        PrimaryColorCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        if (state.marker == null) {
            AllTripsMap(state.tripMarkers)
        } else {
            SelectedPlaceMap(state.marker!!)
        }

        if (state.showAddressTextField) {
            TripAddOverlay(
                placeText = state.placeText,
                placeErrorText = state.placeErrorText,
                onSearchTextChange = { viewModel.onAddressSearchTextChange(it) },
                showConfirm = state.showConfirmAddressButton,
                expandDropdown = state.expandAddressPredictions && state.placeTextChangedByUser,
                showExtraFields = state.showExtraFields,
                predictions = state.predictions,
                nameText = state.nameText,
                arrivalDate = state.arrivalDate,
                departureDate = state.departureDate,
                isSavingEnabled = state.isSavingTripEnabled,
                isAddressEditEnabled = state.isAddressEditEnabled,
                multiStop = state.multiStop,
                onBackClick = { viewModel.onBackEditingAddress() },
                onConfirmClick = { viewModel.onConfirmAddress() },
                onAddressPredictionClick = { viewModel.onAddressSelect(it.id) },
                onNameTextChange = { viewModel.onTripNameChange(it) },
                onArrivalDateChange = { year, month, day ->
                    viewModel.onArrivalDateChange(
                        year,
                        month,
                        day
                    )
                },
                onDepartureDateChange = { year, month, day ->
                    viewModel.onDepartureDateChange(
                        year,
                        month,
                        day
                    )
                },
                onPositiveClick = { viewModel.onTripConfirmClick() },
                onNegativeClick = { viewModel.onTripCancelClick() },
                onMiddleClick = { viewModel.onAddNextStopClick() },
                keyboard = keyboard
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

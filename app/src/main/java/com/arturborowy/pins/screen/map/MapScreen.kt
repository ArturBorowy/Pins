package com.arturborowy.pins.screen.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.AddressPrediction
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PrimaryColorCircularProgressIndicator
import com.arturborowy.pins.ui.composable.map.AllTripsMap
import com.arturborowy.pins.ui.composable.map.SelectedPlaceMap
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.showShortToast

@Composable
fun MapScreen(viewModel: MapViewModel = mapViewModel(false)) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { showShortToast(context, it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background)
    ) {
        PrimaryColorCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        if (state.marker == null) {
            AllTripsMap(state.tripMarkers)
        } else {
            SelectedPlaceMap(state.marker!!)
        }

        MapScreenOverlays(
            showAddressTextField = state.showAddressTextField,
            placeText = state.placeText,
            placeErrorText = state.placeErrorText,
            showConfirmAddressButton = state.showConfirmAddressButton,
            predictions = state.predictions,
            placeTextChangedByUser = state.placeTextChangedByUser,
            showExtraFields = state.showExtraFields,
            nameText = state.nameText,
            arrivalDate = state.arrivalDate,
            departureDate = state.departureDate,
            isSavingTripEnabled = state.isSavingTripEnabled,
            isAddressEditEnabled = state.isAddressEditEnabled,
            multiStop = state.multiStop,
            showAddPinButton = state.showAddPinButton,
            showTripTypeBar = state.showTripTypeBar,
            onAddressSearchTextChange = { viewModel.onAddressSearchTextChange(it) },
            onBackEditingAddress = { viewModel.onBackEditingAddress() },
            onConfirmAddress = { viewModel.onConfirmAddress() },
            onAddressPredictionClick = { viewModel.onAddressSelect(it.id) },
            onTripNameChange = { viewModel.onTripNameChange(it) },
            onArrivalDateChange = { year, month, day ->
                viewModel.onArrivalDateChange(year, month, day)
            },
            onDepartureDateChange = { year, month, day ->
                viewModel.onDepartureDateChange(year, month, day)
            },
            onTripConfirmClick = { viewModel.onTripConfirmClick() },
            onTripCancelClick = { viewModel.onTripCancelClick() },
            onAddNextStopClick = { viewModel.onAddNextStopClick() },
            onAddTripClick = { viewModel.onAddTripClick() },
            onAddSingleStopTripClick = { viewModel.onAddSingleStopTripClick() },
            onAddMultiStopTripClick = { viewModel.onAddMultiStopTripClick() },
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BoxScope.MapScreenOverlays(
    showAddressTextField: Boolean,
    placeText: String,
    placeErrorText: String?,
    showConfirmAddressButton: Boolean,
    predictions: List<AddressPrediction>,
    placeTextChangedByUser: Boolean,
    showExtraFields: Boolean,
    nameText: String,
    arrivalDate: String?,
    departureDate: String?,
    isSavingTripEnabled: Boolean,
    isAddressEditEnabled: Boolean,
    multiStop: Boolean,
    showAddPinButton: Boolean,
    showTripTypeBar: Boolean,
    onAddressSearchTextChange: (String) -> Unit,
    onBackEditingAddress: () -> Unit,
    onConfirmAddress: () -> Unit,
    onAddressPredictionClick: (AddressPrediction) -> Unit,
    onTripNameChange: (String) -> Unit,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onTripConfirmClick: () -> Unit,
    onTripCancelClick: () -> Unit,
    onAddNextStopClick: () -> Unit,
    onAddTripClick: () -> Unit,
    onAddSingleStopTripClick: () -> Unit,
    onAddMultiStopTripClick: () -> Unit,
) {
    if (showAddressTextField) {
        TripAddOverlay(
            placeText = placeText,
            placeErrorText = placeErrorText,
            onSearchTextChange = onAddressSearchTextChange,
            showConfirm = showConfirmAddressButton,
            expandDropdown = predictions.isNotEmpty() && placeTextChangedByUser,
            showExtraFields = showExtraFields,
            predictions = predictions,
            nameText = nameText,
            arrivalDate = arrivalDate,
            departureDate = departureDate,
            isSavingEnabled = isSavingTripEnabled,
            isAddressEditEnabled = isAddressEditEnabled,
            multiStop = multiStop,
            onBackClick = onBackEditingAddress,
            onConfirmClick = onConfirmAddress,
            onAddressPredictionClick = onAddressPredictionClick,
            onNameTextChange = onTripNameChange,
            onArrivalDateChange = onArrivalDateChange,
            onDepartureDateChange = onDepartureDateChange,
            onPositiveClick = onTripConfirmClick,
            onNegativeClick = onTripCancelClick,
            onMiddleClick = onAddNextStopClick,
        )
    } else if (showAddPinButton) {
        Fab(
            R.drawable.ic_add_trip,
            R.string.main_bottom_nav_label_add,
            Modifier
                .align(Alignment.BottomCenter)
                .padding(BrandTheme.spacing.fabMargin),
        ) { onAddTripClick() }
    } else if (showTripTypeBar) {
        AddTripTypesBar(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(BrandTheme.spacing.fabMargin),
            { onAddSingleStopTripClick() },
            { onAddMultiStopTripClick() })
    }
}

package com.arturborowy.pins.screen.map

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.tripcard.TripAddCard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun TripAddOverlay(
    state: MapViewModel.State,
    setState: (MapViewModel.State) -> Unit,
    viewModel: MapViewModel,
    keyboard: SoftwareKeyboardController?
) {
    TripAddCard(
        modifier = Modifier.padding(8.dp),
        placeText = state.placeText,
        placeErrorText = state.placeErrorText,
        onSearchTextChange = {
            setState(state.copy(placeText = it, placeTextChangedByUser = true))
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
}

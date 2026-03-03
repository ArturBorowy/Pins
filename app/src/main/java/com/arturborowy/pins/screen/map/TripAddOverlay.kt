package com.arturborowy.pins.screen.map

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.arturborowy.pins.R
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.ui.composable.tripcard.TripAddCard
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun TripAddOverlay(
    placeText: String,
    placeErrorText: String?,
    onSearchTextChange: (String) -> Unit,
    showConfirm: Boolean,
    expandDropdown: Boolean,
    showExtraFields: Boolean,
    predictions: List<AddressPredictionDto>,
    nameText: String,
    arrivalDate: String?,
    departureDate: String?,
    isSavingEnabled: Boolean,
    isAddressEditEnabled: Boolean,
    multiStop: Boolean,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onAddressPredictionClick: (AddressPredictionDto) -> Unit,
    onNameTextChange: (String) -> Unit,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    onMiddleClick: () -> Unit,
    keyboard: SoftwareKeyboardController?
) {
    TripAddCard(
        modifier = Modifier.padding(PinsTheme.spacing.small),
        placeText = placeText,
        placeErrorText = placeErrorText,
        onSearchTextChange = onSearchTextChange,
        onBackClick = onBackClick,
        showBackArrow = true,
        onConfirmClick = onConfirmClick,
        showConfirm = showConfirm,
        expandDropdown = expandDropdown,
        showExtraEditionFields = showExtraFields,
        predictions = predictions,
        onAddressPredictionClick = onAddressPredictionClick,
        nameText = nameText,
        onNameTextChange = onNameTextChange,
        arrivalDate = arrivalDate,
        onArrivalDateChange = onArrivalDateChange,
        departureDate = departureDate,
        onDepartureDateChange = onDepartureDateChange,
        onPositiveClick = onPositiveClick,
        positiveClickText = stringResource(R.string.create_trip_btn_confirm),
        onNegativeClick = onNegativeClick,
        negativeClickText = stringResource(R.string.create_trip_btn_cancel),
        onMiddleClick = onMiddleClick,
        middleClickText = stringResource(R.string.create_trip_btn_next_stop),
        keyboard = keyboard,
        isSavingEnabled = isSavingEnabled,
        isAddressEditEnabled = isAddressEditEnabled,
        multiStop = multiStop
    )
}

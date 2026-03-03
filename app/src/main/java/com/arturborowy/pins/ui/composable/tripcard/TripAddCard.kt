package com.arturborowy.pins.ui.composable.tripcard

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.pins.R
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.composable.WideCard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TripAddCard(
    modifier: Modifier = Modifier,
    placeText: String,
    placeErrorText: String? = null,
    onSearchTextChange: (String) -> Unit,
    nameText: String,
    onNameTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    showBackArrow: Boolean,
    onConfirmClick: () -> Unit,
    showConfirm: Boolean,
    expandDropdown: Boolean,
    showExtraEditionFields: Boolean,
    predictions: List<AddressPredictionDto>,
    onAddressPredictionClick: (AddressPredictionDto) -> Unit,
    arrivalDate: String?,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    departureDate: String?,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onPositiveClick: () -> Unit,
    positiveClickText: String,
    onNegativeClick: () -> Unit,
    negativeClickText: String,
    onMiddleClick: (() -> Unit)?,
    middleClickText: String?,
    isSavingEnabled: Boolean,
    isAddressEditEnabled: Boolean,
    multiStop: Boolean
) {
    WideCard(modifier = modifier) {
        SearchField(
            placeText = placeText,
            errorText = placeErrorText,
            onTextChange = onSearchTextChange,
            onBackClick = onBackClick,
            showBackArrow = showBackArrow,
            onConfirmClick = onConfirmClick,
            showConfirm = showConfirm,
            isAddressEditEnabled = isAddressEditEnabled,
            showExtraEditionFields = showExtraEditionFields
        )
        SearchResults(
            expandDropdown = expandDropdown,
            predictions = predictions,
            onAddressPredictionClick = onAddressPredictionClick
        )

        if (showExtraEditionFields) {
            if (multiStop) {
                TripMultiStopExtraFields(
                    nameText = nameText,
                    onNameTextChange = onNameTextChange,
                    arrivalDate = arrivalDate,
                    onArrivalDateChange = onArrivalDateChange,
                    onPositiveClick = onPositiveClick,
                    positiveClickText = positiveClickText,
                    onNegativeClick = onNegativeClick,
                    negativeClickText = negativeClickText,
                    onMiddleClick = onMiddleClick,
                    middleClickText = middleClickText,
                    isSavingEnabled = isSavingEnabled,
                )
            } else {
                TripSingleStopExtraFields(
                    nameText = nameText,
                    onNameTextChange = onNameTextChange,
                    arrivalDate = arrivalDate,
                    onArrivalDateChange = onArrivalDateChange,
                    departureDate = departureDate,
                    onDepartureDateChange = onDepartureDateChange,
                    onPositiveClick = onPositiveClick,
                    positiveClickText = positiveClickText,
                    onNegativeClick = onNegativeClick,
                    negativeClickText = negativeClickText,
                    isSavingEnabled = isSavingEnabled,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun TripAddCardPreview() = PreviewTheme {
    TripAddCard(
        placeText = "Place text",
        placeErrorText = "No internet connection",
        onSearchTextChange = {},
        nameText = "Name text",
        onNameTextChange = {},
        onBackClick = { },
        showBackArrow = true,
        onConfirmClick = { },
        showConfirm = true,
        expandDropdown = false,
        showExtraEditionFields = true,
        predictions = listOf(),
        onAddressPredictionClick = {},
        arrivalDate = stringResource(R.string.add_trip_hint_arrival_date),
        onArrivalDateChange = { i: Int, i1: Int, i2: Int -> },
        departureDate = stringResource(R.string.add_trip_hint_departure_date),
        onDepartureDateChange = { i: Int, i1: Int, i2: Int -> },
        onPositiveClick = {},
        positiveClickText = "CONFIRM",
        onNegativeClick = {},
        middleClickText = "NEXT",
        onMiddleClick = {},
        negativeClickText = "CLOSE",
        isSavingEnabled = true,
        isAddressEditEnabled = true,
        multiStop = true
    )
}

package com.arturborowy.pins.ui.composable.tripcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.FilledButton
import com.arturborowy.pins.ui.composable.OutlinedButton
import com.arturborowy.pins.ui.composable.PreviewTheme


@Composable
fun TripMultiStopExtraFields(
    nameText: String,
    onNameTextChange: (String) -> Unit,
    arrivalDate: String?,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    onPositiveClick: () -> Unit,
    positiveClickText: String,
    onNegativeClick: () -> Unit,
    negativeClickText: String,
    onMiddleClick: (() -> Unit)?,
    middleClickText: String?,
    isSavingEnabled: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BrandTheme.colorScheme.surface),
    ) {
        TripNameField(
            nameText,
            onNameTextChange
        )
        DatePickingButton(
            label = stringResource(R.string.add_trip_hint_arrival_date),
            date = arrivalDate,
            onDateSelected = onArrivalDateChange,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = BrandTheme.spacing.cardPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                text = negativeClickText,
                onClick = onNegativeClick,
            )
            if (middleClickText != null) {
                FilledButton(
                    text = middleClickText,
                    onClick = { onMiddleClick?.invoke() },
                    enabled = isSavingEnabled,
                )
            }
            FilledButton(
                text = positiveClickText,
                onClick = onPositiveClick,
                enabled = isSavingEnabled,
            )
        }
    }
}

@Preview
@Composable
fun TripMultiStopExtraFieldsPreview() = PreviewTheme {
    TripMultiStopExtraFields(
        nameText = "Name text",
        onNameTextChange = {},
        arrivalDate = stringResource(R.string.add_trip_hint_arrival_date),
        onArrivalDateChange = { i: Int, i1: Int, i2: Int -> },
        onPositiveClick = {},
        positiveClickText = "CONFIRM",
        onNegativeClick = {},
        negativeClickText = "CLOSE",
        onMiddleClick = {},
        middleClickText = "NEXT",
        isSavingEnabled = true,
    )
}

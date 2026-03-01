package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.theme.PinsTheme


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
            .background(PinsTheme.colorScheme.surface),
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
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = PinsTheme.colorScheme.onBackground
                ),
                onClick = { onNegativeClick() }) {
                Text(text = negativeClickText, color = PinsTheme.colorScheme.onBackground)
            }
            if (middleClickText != null) {
                Button(modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                    enabled = isSavingEnabled,
                    colors = ButtonDefaults.buttonColors(containerColor = PinsTheme.colorScheme.primary),
                    onClick = { onMiddleClick?.invoke() }) {
                    Text(text = middleClickText, color = PinsTheme.colorScheme.onPrimary)
                }
            }
            Button(modifier = Modifier
                .weight(1f)
                .padding(8.dp),
                enabled = isSavingEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = PinsTheme.colorScheme.primary),
                onClick = { onPositiveClick() }) {
                Text(text = positiveClickText, color = PinsTheme.colorScheme.onPrimary)
            }
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
        middleClickText = "ADD NEXT",
        isSavingEnabled = true,
    )
}

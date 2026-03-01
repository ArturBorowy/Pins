package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun TripSingleStopExtraFields(
    nameText: String,
    onNameTextChange: (String) -> Unit,
    arrivalDate: String?,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    departureDate: String?,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onPositiveClick: () -> Unit,
    positiveClickText: String,
    onNegativeClick: () -> Unit,
    negativeClickText: String,
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
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DatePickingButton(
                label = stringResource(R.string.add_trip_hint_arrival_date),
                date = arrivalDate,
                onDateSelected = onArrivalDateChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            DatePickingButton(
                label = stringResource(R.string.add_trip_hint_departure_date),
                date = departureDate,
                onDateSelected = onDepartureDateChange,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = PinsTheme.colorScheme.primary
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PinsTheme.colorScheme.primary,
                ),
                onClick = { onNegativeClick() }) {
                Text(text = negativeClickText)
            }
            Button(modifier = Modifier
                .weight(1f)
                .padding(8.dp),
                enabled = isSavingEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = PinsTheme.colorScheme.primary),
                onClick = { onPositiveClick() }) {
                Text(text = positiveClickText)
            }
        }
    }
}

@Preview
@Composable
fun TripSingleStopExtraFieldsPreview() {
    TripSingleStopExtraFields(
        nameText = "Name text",
        onNameTextChange = {},
        arrivalDate = stringResource(R.string.add_trip_hint_arrival_date),
        onArrivalDateChange = { i: Int, i1: Int, i2: Int -> },
        departureDate = stringResource(R.string.add_trip_hint_departure_date),
        onDepartureDateChange = { i: Int, i1: Int, i2: Int -> },
        onPositiveClick = {},
        positiveClickText = "CONFIRM",
        onNegativeClick = {},
        negativeClickText = "CLOSE",
        isSavingEnabled = true,
    )
}

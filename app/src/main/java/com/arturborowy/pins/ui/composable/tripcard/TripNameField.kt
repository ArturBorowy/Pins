package com.arturborowy.pins.ui.composable.tripcard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PinsOutlinedTextField
import com.arturborowy.pins.ui.composable.PreviewTheme

@Composable
fun TripNameField(
    nameText: String,
    onNameTextChange: (String) -> Unit
) {
    PinsOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp, 8.dp, 8.dp),
        value = nameText,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        onValueChange = { onNameTextChange(it) },
        label = { Text(stringResource(R.string.add_trip_hint_trip_name)) },
    )
}

@Preview
@Composable
fun TripNameFieldPreview() = PreviewTheme {
    TripNameField(
        nameText = "Summer in Portugal",
        onNameTextChange = {}
    )
}
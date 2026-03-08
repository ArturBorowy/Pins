package com.arturborowy.pins.ui.composable.tripcard

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.PopupProperties
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.domain.AddressPrediction
import com.arturborowy.pins.ui.composable.PreviewTheme

@Composable
fun SearchResults(
    expandDropdown: Boolean,
    predictions: List<AddressPrediction>,
    onAddressPredictionClick: (AddressPrediction) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.background(BrandTheme.colorScheme.surface),
        expanded = expandDropdown,
        properties = PopupProperties(
            clippingEnabled = false,
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = {}) {
        predictions.forEach { addressPrediction ->
            DropdownMenuItem(onClick = { onAddressPredictionClick(addressPrediction) }, text = {
                Text(
                    addressPrediction.label,
                    color = BrandTheme.colorScheme.onSurface,
                )
            })
        }
    }
}

@Preview
@Composable
fun SearchResultsPreview() = PreviewTheme {
    SearchResults(
        expandDropdown = true,
        predictions = listOf(
            AddressPrediction(id = AddressPrediction.Id("1"), label = "Lisbon, Portugal"),
            AddressPrediction(id = AddressPrediction.Id("2"), label = "London, United Kingdom"),
            AddressPrediction(id = AddressPrediction.Id("3"), label = "Lima, Peru"),
        ),
        onAddressPredictionClick = {}
    )
}
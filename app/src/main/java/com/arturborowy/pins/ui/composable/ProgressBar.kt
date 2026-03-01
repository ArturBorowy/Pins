package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.ui.theme.PinsTheme

@Composable
fun PrimaryColorCircularProgressIndicator(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = PinsTheme.colorScheme.primary,
    )
}

@Preview
@Composable
fun PrimaryColorCircularProgressIndicatorPreview() = PreviewTheme {
    PrimaryColorCircularProgressIndicator(modifier = Modifier)
}
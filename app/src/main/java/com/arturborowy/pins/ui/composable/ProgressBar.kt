package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme

@Composable
fun PrimaryColorCircularProgressIndicator(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(BrandTheme.sizing.progressBarSize),
        color = BrandTheme.colorScheme.primary,
    )
}

@Preview
@Composable
private fun PrimaryColorCircularProgressIndicatorPreview() = PreviewTheme {
    PrimaryColorCircularProgressIndicator(modifier = Modifier)
}

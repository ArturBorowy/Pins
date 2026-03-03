package com.arturborowy.pins.ui.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.brand.designsystem.typography.headlineSmallEmphasized

@Composable
fun PageTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        style = BrandTheme.typography.headlineSmallEmphasized,
        color = BrandTheme.colorScheme.onBackground,
        text = text
    )
}

@Preview
@Composable
fun PageTitlePreview() = PreviewTheme {
    PageTitle(text = "My Trips")
}
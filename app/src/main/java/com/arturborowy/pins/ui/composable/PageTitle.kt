package com.arturborowy.pins.ui.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.headlineSmallEmphasized

@Composable
fun PageTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        style = PinsTheme.typography.headlineSmallEmphasized,
        color = PinsTheme.colorScheme.onBackground,
        text = text
    )
}

@Preview
@Composable
fun PageTitlePreview() = PreviewTheme {
    PageTitle(text = "My Trips")
}
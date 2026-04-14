package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(
            width = BrandTheme.sizing.strokeWidth,
            color = BrandTheme.colorScheme.onBackground
        ),
        onClick = onClick
    ) {
        Text(text = text, color = BrandTheme.colorScheme.onBackground)
    }
}

@Composable
fun FilledButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = BrandTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Text(text = text, color = BrandTheme.colorScheme.onPrimary)
    }
}

@Preview
@Composable
private fun OutlinedButtonPreview() = PreviewTheme {
    OutlinedButton(text = "Cancel", onClick = {})
}

@Preview
@Composable
private fun FilledButtonPreview() = PreviewTheme {
    FilledButton(text = "Confirm", onClick = {})
}

@Preview
@Composable
private fun FilledButtonDisabledPreview() = PreviewTheme {
    FilledButton(text = "Confirm", onClick = {}, enabled = false)
}

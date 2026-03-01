package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.ui.theme.PinsTheme

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        modifier = modifier,
        border = BorderStroke(width = 1.dp, color = PinsTheme.colorScheme.onBackground),
        onClick = onClick
    ) {
        Text(text = text, color = PinsTheme.colorScheme.onBackground)
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
        colors = ButtonDefaults.buttonColors(containerColor = PinsTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Text(text = text, color = PinsTheme.colorScheme.onPrimary)
    }
}

@Preview
@Composable
fun OutlinedButtonPreview() = PreviewTheme {
    OutlinedButton(text = "Cancel", onClick = {})
}

@Preview
@Composable
fun FilledButtonPreview() = PreviewTheme {
    FilledButton(text = "Confirm", onClick = {})
}

@Preview
@Composable
fun FilledButtonDisabledPreview() = PreviewTheme {
    FilledButton(text = "Confirm", onClick = {}, enabled = false)
}
package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.ui.theme.PinsTheme

@Composable
fun PreviewTheme(
    composable: @Composable () -> Unit,
) {
    Column {
        PinsTheme(false, dynamicColor = false, content = composable)
        Spacer(Modifier.height(16.dp))
        PinsTheme(true, dynamicColor = false, content = composable)
    }
}

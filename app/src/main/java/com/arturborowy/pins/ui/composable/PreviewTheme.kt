package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.ui.theme.PinsTheme

@Composable
fun PreviewTheme(
    composable: @Composable () -> Unit,
) {
    Column {
        PinsTheme(false, dynamicColor = false, content = {
            Box(
                Modifier
                    .background(PinsTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                composable()
            }
        })
        Spacer(Modifier.height(16.dp))
        PinsTheme(true, dynamicColor = false, content = {
            Box(
                Modifier
                    .background(PinsTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                composable()
            }
        })
    }
}

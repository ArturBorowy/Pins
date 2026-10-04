package com.arturborowy.pins.ui.composable.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.ui.composable.PreviewLightAndDark
import com.arturborowy.pins.ui.composable.PreviewTheme

private val placeholderColor = Color(0x6602de3d)
private val strokeDp = 4.dp
private val stepDp = 16.dp

@Composable
fun Placeholder(modifier: Modifier = Modifier) {
    Canvas(
        modifier
            .clipToBounds()
            .fillMaxSize()
    ) {

        val width = size.width
        val height = size.height
        var x = -height

        val strokePx = strokeDp.toPx()
        val stepPx = stepDp.toPx()

        while (x < width) {
            drawLine(
                color = placeholderColor,
                start = Offset(x, 0f),
                end = Offset(x + height, height),
                strokeWidth = strokePx
            )
            x += stepPx
        }
    }
}

@PreviewLightAndDark
@Composable
private fun PlaceholderPreview() = PreviewTheme {
    Placeholder()
}

package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.brand.designsystem.BrandTheme

@Composable
fun WideCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .shadow(BrandTheme.sizing.shadow, RoundedCornerShape(8.dp))
            .background(BrandTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(8.dp))
            .padding(BrandTheme.spacing.cardPadding)
            .fillMaxWidth(),
        content = content
    )
}

@Preview
@Composable
private fun WideCardPreview() = PreviewTheme {
    WideCard {
        Text(text = "Card content")
    }
}
package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.Spacing

@Composable
fun WideCard(
    modifier: Modifier = Modifier,
    margin: PaddingValues = PaddingValues(bottom = Spacing().medium),
    padding: PaddingValues = PaddingValues(Spacing().medium),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(margin)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(PinsTheme.colorScheme.surface)
                .padding(padding)
                .fillMaxWidth(),
            content = content
        )
    }
}

@Preview
@Composable
fun WideCardPreview() = PreviewTheme {
    WideCard {
        Text(text = "Card content")
    }
}
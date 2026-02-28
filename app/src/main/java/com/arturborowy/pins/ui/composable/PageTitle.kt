package com.arturborowy.pins.ui.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arturborowy.pins.ui.theme.PinsTheme

@Composable
fun PageTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = PinsTheme.colorScheme.onBackground,
        text = text
    )
}
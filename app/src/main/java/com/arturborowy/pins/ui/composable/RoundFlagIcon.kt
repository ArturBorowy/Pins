package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.domain.Country
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.flags.R as FlagsR

@Composable
fun RoundFlagIcon(country: Country) {
    Card(
        shape = CircleShape,
        border = BorderStroke(1.dp, PinsTheme.colorScheme.primary),
    ) {
        Image(
            modifier = Modifier
                .height(24.dp)
                .width(24.dp),
            painter = painterResource(country.countryIcon),
            contentDescription = country.countryLabel
        )
    }
}

@Preview
@Composable
fun RoundFlagIconPreview() = PreviewTheme {
    RoundFlagIcon(
        country = Country(
            countryId = "pl",
            countryLabel = "Poland",
            countryIcon = FlagsR.drawable.pl
        )
    )
}
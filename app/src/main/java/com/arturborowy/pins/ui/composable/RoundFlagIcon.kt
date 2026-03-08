package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.domain.Country
import com.arturborowy.flags.R as FlagsR

@Composable
fun RoundFlagIcon(country: Country) {
    Card(
        shape = CircleShape,
        border = BorderStroke(BrandTheme.sizing.strokeWidth, BrandTheme.colorScheme.primary),
    ) {
        Image(
            modifier = Modifier
                .size(BrandTheme.sizing.icon),
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
            countryId = Country.Id("pl"),
            countryLabel = "Poland",
            countryIcon = FlagsR.drawable.pl
        )
    )
}
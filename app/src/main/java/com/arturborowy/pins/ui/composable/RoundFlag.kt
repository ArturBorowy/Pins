package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.Country

@Composable
fun RoundFlag(country: Country) {
    Card(
        shape = RoundedCornerShape(999.dp),
        border = BorderStroke(1.dp, colorResource(R.color.primary)),
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
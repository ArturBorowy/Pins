package com.arturborowy.pins.screen.map

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PreviewTheme

@Composable
fun AddTripTypesBar(
    modifier: Modifier, onSingleStopTripClick: () -> Unit, onMultipleStopTripClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(percent = 100),
        colors = CardDefaults.cardColors(containerColor = BrandTheme.colorScheme.surfaceContainerHigh),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = BrandTheme.sizing.shadow)
    ) {
        Row {
            TextButton(
                onClick = { onSingleStopTripClick() },
                shape = RoundedCornerShape(topStartPercent = 100, bottomStartPercent = 100)
            ) {
                Text(
                    text = stringResource(R.string.add_trip_btn_single_stop),
                    color = BrandTheme.colorScheme.onSurface,
                    style = BrandTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(
                            BrandTheme.spacing.buttonPadding,
                            BrandTheme.spacing.buttonPadding,
                            0.dp,
                            BrandTheme.spacing.buttonPadding
                        )
                        .align(Alignment.CenterVertically)
                )
            }

            Divider(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .width(2.dp)
                    .height(30.dp)
                    .align(Alignment.CenterVertically),
                color = BrandTheme.colorScheme.primary
            )

            TextButton(
                onClick = { onMultipleStopTripClick() },
                shape = RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100)
            ) {
                Text(
                    text = stringResource(R.string.add_trip_btn_multi_stop),
                    color = BrandTheme.colorScheme.onSurface,
                    style = BrandTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(
                            0.dp,
                            BrandTheme.spacing.buttonPadding,
                            BrandTheme.spacing.buttonPadding,
                            BrandTheme.spacing.buttonPadding
                        )
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Preview
@Composable
fun AddTripTypesBarPreview() = PreviewTheme {
    AddTripTypesBar(
        modifier = Modifier,
        onSingleStopTripClick = {},
        onMultipleStopTripClick = {}
    )
}

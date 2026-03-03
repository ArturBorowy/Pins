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
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.spacing

@Composable
fun AddTripTypesBar(
    modifier: Modifier, onSingleStopTripClick: () -> Unit, onMultipleStopTripClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(percent = 100),
        colors = CardDefaults.cardColors(containerColor = PinsTheme.colorScheme.surface),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row {
            TextButton(
                onClick = { onSingleStopTripClick() },
                shape = RoundedCornerShape(topStartPercent = 100, bottomStartPercent = 100)
            ) {
                Text(
                    text = stringResource(R.string.add_trip_btn_single_stop),
                    color = PinsTheme.colorScheme.onSurface,
                    style = PinsTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(
                            PinsTheme.spacing.xSmall,
                            PinsTheme.spacing.xSmall,
                            0.dp,
                            PinsTheme.spacing.xSmall
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
                color = PinsTheme.colorScheme.primary
            )

            TextButton(
                onClick = { onMultipleStopTripClick() },
                shape = RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100)
            ) {
                Text(
                    text = stringResource(R.string.add_trip_btn_multi_stop),
                    color = PinsTheme.colorScheme.onSurface,
                    style = PinsTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(
                            0.dp,
                            PinsTheme.spacing.xSmall,
                            PinsTheme.spacing.xSmall,
                            PinsTheme.spacing.xSmall
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

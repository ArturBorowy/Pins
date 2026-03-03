package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.brand.designsystem.typography.bodyLargeEmphasized
import com.arturborowy.brand.designsystem.typography.labelMediumEmphasized
import com.arturborowy.brand.designsystem.typography.titleMediumEmphasized
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.Country
import com.arturborowy.pins.screen.triplist.TripListItem
import com.arturborowy.pins.screen.triplist.TripListItemStopItem
import com.arturborowy.flags.R as FlagsR

object TripViewTag {
    const val TRIP_NAME = "TRIP_NAME"
    const val TRIP_PLACE = "TRIP_PLACE"
    const val TRIP_DATES = "TRIP_DATES"
}

@Composable
fun TripView(tripListItem: TripListItem, onEditTripClick: (TripListItem) -> Unit) {
    WideCard {
        Column {
            TripHeader(tripListItem, onEditTripClick)
            tripListItem.stops.forEach { stop ->
                TripRow(stop)
            }
        }
    }
}

@Composable
fun TripHeader(tripListItem: TripListItem, onEditTripClick: (TripListItem) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RoundFlagIcon(tripListItem.stops[0].country)

        Text(
            modifier = Modifier
                .padding(start = BrandTheme.spacing.textSpacing)
                .testTag(TripViewTag.TRIP_NAME),
            style = BrandTheme.typography.titleMediumEmphasized,
            color = BrandTheme.colorScheme.onSurface,
            text = tripListItem.name
        )

        Spacer(Modifier.weight(1.0f))

        IconButton(
            modifier = Modifier
                .size(20.dp),
            onClick = { onEditTripClick(tripListItem) }) {
            Icon(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = stringResource(
                    R.string.trip_list_cd_edit,
                    tripListItem.name
                )
            )
        }
    }
}

@Composable
fun TripRow(tripListItemStopItem: TripListItemStopItem) {
    Row(
        modifier = Modifier.padding(start = 10.dp, top = BrandTheme.spacing.textSpacing)
    ) {
        Divider(
            color = BrandTheme.colorScheme.primary,
            modifier = Modifier
                .height(20.dp)
                .width(3.dp)
        )

        Text(
            modifier = Modifier
                .padding(start = BrandTheme.spacing.textSpacing)
                .testTag(TripViewTag.TRIP_PLACE),
            style = BrandTheme.typography.bodyLargeEmphasized,
            color = BrandTheme.colorScheme.onSurface,
            text = tripListItemStopItem.locationName
        )

        Spacer(Modifier.weight(1.0f))

        Text(
            modifier = Modifier.testTag(TripViewTag.TRIP_DATES),
            style = BrandTheme.typography.labelMediumEmphasized,
            color = BrandTheme.colorScheme.onSurface,
            text = tripListItemStopItem.dateStr
        )
    }
}

private val previewCountry = Country(
    countryId = "pl",
    countryLabel = "Poland",
    countryIcon = FlagsR.drawable.pl
)

private val previewStop = TripListItemStopItem(
    locationName = "Warsaw",
    dateStr = "01.03.2026",
    country = previewCountry
)

private val previewTrip = TripListItem(
    id = 1L,
    name = "Poland Trip",
    stops = listOf(previewStop, previewStop.copy(locationName = "Kraków", dateStr = "05.03.2026"))
)

@Preview
@Composable
fun TripViewPreview() = PreviewTheme {
    TripView(tripListItem = previewTrip, onEditTripClick = {})
}

@Preview
@Composable
fun TripRowPreview() = PreviewTheme {
    TripRow(tripListItemStopItem = previewStop)
}
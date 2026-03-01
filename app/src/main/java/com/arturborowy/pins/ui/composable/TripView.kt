package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.triplist.TripListItem
import com.arturborowy.pins.screen.triplist.TripListItemStopItem
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.bodyLargeEmphasized
import com.arturborowy.pins.ui.theme.labelMediumEmphasized
import com.arturborowy.pins.ui.theme.titleMediumEmphasized

object TripViewTag {
    const val TRIP_NAME = "TRIP_NAME"
    const val TRIP_PLACE = "TRIP_PLACE"
    const val TRIP_DATES = "TRIP_DATES"
}

@Composable
fun TripView(tripListItem: TripListItem, onEditTripClick: (TripListItem) -> Unit) {
    WideCard(margin = PaddingValues(16.dp, 0.dp, 16.dp, 16.dp)) {
        TripHeader(tripListItem, onEditTripClick)
        tripListItem.stops.forEach { stop ->
            TripRow(stop)
        }
    }
}

@Composable
fun TripHeader(tripListItem: TripListItem, onEditTripClick: (TripListItem) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RoundFlag(tripListItem.stops[0].country)

        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .testTag(TripViewTag.TRIP_NAME),
            style = PinsTheme.typography.titleMediumEmphasized,
            color = PinsTheme.colorScheme.onSurface,
            text = tripListItem.name
        )

        Spacer(Modifier.weight(1.0f))

        IconButton(
            modifier = Modifier
                .height(20.dp)
                .width(20.dp),
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
        modifier = Modifier.padding(10.dp, 8.dp, 0.dp, 0.dp)
    ) {
        Divider(
            color = PinsTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxHeight()
                .height(20.dp)
                .width(3.dp)
        )

        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .testTag(TripViewTag.TRIP_PLACE),
            style = PinsTheme.typography.bodyLargeEmphasized,
            color = PinsTheme.colorScheme.onSurface,
            text = tripListItemStopItem.locationName
        )

        Spacer(Modifier.weight(1.0f))

        Text(
            modifier = Modifier.testTag(TripViewTag.TRIP_DATES),
            style = PinsTheme.typography.labelMediumEmphasized,
            color = PinsTheme.colorScheme.onSurface,
            text = tripListItemStopItem.dateStr
        )
    }
}
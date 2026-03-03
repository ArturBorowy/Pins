package com.arturborowy.pins.screen.triplist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.Country
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.composable.TripView
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.headlineSmallEmphasized
import com.arturborowy.pins.ui.theme.spacing

@Composable
fun TripListView(trips: List<TripListItem>, onEditTripClick: (TripListItem) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(PinsTheme.spacing.medium),
                style = PinsTheme.typography.headlineSmallEmphasized,
                color = PinsTheme.colorScheme.onBackground,
                text = stringResource(R.string.trip_list_header)
            )
        }
        items(trips) { TripView(it, onEditTripClick) }
    }
}

@Preview
@Composable
fun TripListViewPreview() = PreviewTheme {
    TripListView(
        listOf(
            TripListItem(
                1L, "Portugal",
                listOf(
                    TripListItemStopItem(
                        "Porto", "11-11-2011",
                        Country("1", "Portugal", com.arturborowy.flags.R.drawable.pt)
                    ),
                    TripListItemStopItem(
                        "Lisboa", "15-11-2011",
                        Country("1", "Portugal", com.arturborowy.flags.R.drawable.pt)
                    )
                )
            )
        )
    ) { }
}
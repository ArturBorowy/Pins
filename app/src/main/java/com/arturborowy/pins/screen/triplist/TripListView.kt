package com.arturborowy.pins.screen.triplist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.Country
import com.arturborowy.pins.domain.Trip
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.composable.TripView

@Composable
fun TripListView(trips: List<TripListItem>, onEditTripClick: (TripListItem) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = BrandTheme.spacing.cardPadding)
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(BrandTheme.spacing.cardPadding)
    ) {
        item { Spacer(Modifier.height(BrandTheme.spacing.cardPadding)) }

        item { PageTitle(stringResource(R.string.trip_list_header)) }

        items(trips) { TripView(it, onEditTripClick) }

        item { Spacer(Modifier.height(BrandTheme.spacing.cardPadding)) }
    }
}

@Preview
@Composable
private fun TripListViewPreview() = PreviewTheme {
    TripListView(
        listOf(
            TripListItem(
                Trip.Id(1L), "Portugal",
                listOf(
                    TripListItemStopItem(
                        "Porto", "11-11-2011",
                        Country(Country.Id("1"), "Portugal", com.arturborowy.flags.R.drawable.pt)
                    ),
                    TripListItemStopItem(
                        "Lisboa", "15-11-2011",
                        Country(Country.Id("1"), "Portugal", com.arturborowy.flags.R.drawable.pt)
                    )
                )
            )
        )
    ) { }
}

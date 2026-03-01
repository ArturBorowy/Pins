package com.arturborowy.pins.screen.triplist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.PrimaryColorCircularProgressIndicator
import com.arturborowy.pins.ui.composable.TripView
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.headlineSmallEmphasized
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun TripsListScreen(viewModel: TripsListViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
            .navigationBarsPadding(),
    ) {
        if (state.isLoading) {
            PrimaryColorCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.tripDetails.isEmpty()) {
            EmptyListView { viewModel.onAddTripClick() }
        } else {
            TripListView(state.tripDetails) { viewModel.onEditTripClick(it) }
        }
    }
}

@Composable
fun BoxScope.EmptyListView(onAddTrip: () -> Unit) {
    PageTitle(
        text = stringResource(R.string.trip_list_header_empty),
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
    )
    Fab(
        R.drawable.ic_add_trip,
        R.string.main_bottom_nav_label_add,
        Modifier
            .padding(16.dp)
            .align(Alignment.Center),
    ) { onAddTrip() }
    PageTitle(
        text = stringResource(R.string.trip_list_footer_empty),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp, 16.dp, 16.dp, 16.dp)
    )
}

@Composable
fun TripListView(trips: List<Any>, onEditTripClick: (TripListItem) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 16.dp),
                style = PinsTheme.typography.headlineSmallEmphasized,
                color = PinsTheme.colorScheme.onBackground,
                text = stringResource(R.string.trip_list_header)
            )
        }
        items(trips) {
            if (it is TripListItem) {
                TripView(it, onEditTripClick)
            }
        }
    }
}
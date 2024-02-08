package com.arturborowy.pins.screen.triplist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.CircularProgressBar
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.TripView
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.pxToDp
import com.arturborowy.pins.utils.statusBarHeightPx

@Composable
fun TripsListScreen(viewModel: TripsListViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    val androidStatusBarHeight = pxToDp(LocalContext.current.statusBarHeightPx ?: 0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary))
            .padding(0.dp, androidStatusBarHeight, 0.dp, 0.dp)
    ) {
        if (state.isLoading) {
            CircularProgressBar(modifier = Modifier.align(Alignment.Center))
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
fun TripListView(trips: List<TripSingleStop>, onEditTripClick: (TripSingleStop) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary))
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(16.dp, 16.dp, 16.dp, 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White,
                text = stringResource(R.string.trip_list_header)
            )
        }
        items(trips) {
            TripView(it, onEditTripClick)
        }
    }
}
package com.arturborowy.pins.screen.triplist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.ui.composable.PrimaryColorCircularProgressIndicator
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun TripsListScreen(viewModel: TripsListViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background),
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


package com.arturborowy.pins.screen.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PrimaryColorCircularProgressIndicator
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.showShortToast

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel = mapViewModel(false)) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    if (state.errorText != null) {
        showShortToast(LocalContext.current, state.errorText)
        setState(state.copy(errorText = null))
    }

    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
    ) {
        PrimaryColorCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        if (state.placeLongitude != null && state.placeLatitude != null && state.placeCountryIcon != null) {
            SelectedPlaceMap(state, context)
        } else {
            TripsMap(state, context)
        }

        if (state.showAddressTextField) {
            TripAddOverlay(state, setState, viewModel, keyboard)
        } else if (state.showAddPinButton) {
            Fab(
                R.drawable.ic_add_trip,
                R.string.main_bottom_nav_label_add,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            ) { viewModel.onAddTripClick() }
        } else if (state.showTripTypeBar) {
            AddTripTypesBar(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                { viewModel.onAddSingleStopTripClick() },
                { viewModel.onAddMultiStopTripClick() })
        }
    }
}

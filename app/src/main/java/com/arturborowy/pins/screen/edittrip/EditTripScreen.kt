package com.arturborowy.pins.screen.edittrip

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.screen.map.MapMarkerItem
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.ui.composable.map.SelectedPlaceMap
import com.arturborowy.pins.ui.composable.tripcard.TripAddCard
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.showShortToast
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditTripScreen(viewModel: EditTripViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { showShortToast(context, it) }
    }

    val pagerState = rememberPagerState { state.stops.size }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                viewModel.onPageChanged(page)
            }
    }
    LaunchedEffect(state.currentStopId) {
        pagerState.scrollToPage(state.currentStopId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier.alpha(if (state.isPreviousStopAvailable) 1f else 0f),
                onClick = { viewModel.onPreviousStopClick() }) {
                Icon(
                    modifier = Modifier
                        .size(BrandTheme.sizing.icon),
                    painter = painterResource(R.drawable.ic_arrow_back),
                    tint = BrandTheme.colorScheme.onBackground,
                    contentDescription = stringResource(R.string.edit_trip_cd_previous_stop)
                )
            }
            PageTitle(
                text = stringResource(R.string.edit_trip_header),
            )

            IconButton(
                modifier = Modifier.alpha(if (state.isNextStopAvailable) 1f else 0f),
                onClick = { viewModel.onNextStopClick() }) {
                Icon(
                    modifier = Modifier
                        .size(BrandTheme.sizing.icon),
                    painter = painterResource(R.drawable.ic_arrow_forward),
                    tint = BrandTheme.colorScheme.onBackground,
                    contentDescription = stringResource(R.string.edit_trip_cd_next_stop)
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            val stop = state.stops.getOrNull(page)

            stop?.let {
                EditStop(
                    viewModel,
                    state,
                    stop
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditStop(
    viewModel: EditTripViewModel,
    state: EditTripViewModel.State,
    stop: EditTripStopItem
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(BrandTheme.spacing.screenPadding)
    ) {
        TripAddCard(
            placeText = stop.locationName,
            placeErrorText = state.placeErrorText,
            onSearchTextChange = { viewModel.onSearchTextChange(placeText = it) },
            onBackClick = { viewModel.onBackEditingAddress() },
            showBackArrow = true,
            onConfirmClick = { viewModel.onConfirmAddress() },
            showConfirm = state.showConfirmAddressButton,
            expandDropdown = state.expandAddressPredictions && state.placeTextChangedByUser,
            showExtraEditionFields = state.showExtraFields,
            predictions = state.predictions,
            onAddressPredictionClick = { viewModel.onAddressSelect(it.id) },
            nameText = state.tripName,
            onNameTextChange = { viewModel.onTripNameChange(it) },
            arrivalDate = stop.arrivalDateStr,
            onArrivalDateChange = { year: Int, month: Int, dayOfMonth: Int ->
                viewModel.onArrivalDateChange(year, month, dayOfMonth)
            },
            departureDate = stop.departureDateStr,
            onDepartureDateChange = { year: Int, month: Int, dayOfMonth: Int ->
                viewModel.onDepartureDateChange(year, month, dayOfMonth)
            },
            onPositiveClick = { viewModel.onSaveChangesClick() },
            positiveClickText = stringResource(R.string.edit_trip_btn_save_changes),
            onNegativeClick = { viewModel.onTripRemoveClick() },
            negativeClickText = stringResource(R.string.edit_trip_btn_delete),
            onMiddleClick = null,
            middleClickText = null,
            isSavingEnabled = state.isSavingTripEnabled,
            isAddressEditEnabled = state.isAddressEditEnabled,
            multiStop = state.stops.size > 1
        )
        WideCard {
            SelectedPlaceMap(
                MapMarkerItem(
                    stop.locationName,
                    stop.country.countryIcon,
                    stop.latitude,
                    stop.longitude
                )
            )
        }
    }
}
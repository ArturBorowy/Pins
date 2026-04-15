package com.arturborowy.pins.screen.edittrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.AddressPrediction
import com.arturborowy.pins.domain.Country
import com.arturborowy.pins.screen.map.MapMarkerItem
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.ui.composable.map.SelectedPlaceMap
import com.arturborowy.pins.ui.composable.tripcard.TripAddCard
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.showShortToast
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun EditTripScreen(viewModel: EditTripViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsStateWithLifecycle()

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
        EditTripTopBar(
            isPreviousStopAvailable = state.isPreviousStopAvailable,
            onPreviousStopClick = { viewModel.onPreviousStopClick() },
            isNextStopAvailable = state.isNextStopAvailable,
            onNextStopClick = { viewModel.onNextStopClick() }
        )

        HorizontalPager(state = pagerState) { page ->
            val stop = state.stops.getOrNull(page)
            stop?.let {
                EditStop(
                    stop = stop,
                    tripName = state.tripName,
                    predictions = state.predictions,
                    placeErrorText = state.placeErrorText,
                    showConfirmAddressButton = state.showConfirmAddressButton,
                    showExtraFields = state.showExtraFields,
                    isSavingTripEnabled = state.isSavingTripEnabled,
                    isAddressEditEnabled = state.isAddressEditEnabled,
                    placeTextChangedByUser = state.placeTextChangedByUser,
                    multiStop = state.stops.size > 1,
                    onSearchTextChange = { viewModel.onSearchTextChange(it) },
                    onBackEditingAddress = { viewModel.onBackEditingAddress() },
                    onConfirmAddress = { viewModel.onConfirmAddress() },
                    onAddressSelect = { viewModel.onAddressSelect(it.id) },
                    onTripNameChange = { viewModel.onTripNameChange(it) },
                    onArrivalDateChange = { year, month, day ->
                        viewModel.onArrivalDateChange(year, month, day)
                    },
                    onDepartureDateChange = { year, month, day ->
                        viewModel.onDepartureDateChange(year, month, day)
                    },
                    onSaveChangesClick = { viewModel.onSaveChangesClick() },
                    onTripRemoveClick = { viewModel.onTripRemoveClick() }
                )
            }
        }
    }
}

@Composable
fun EditTripTopBar(
    isPreviousStopAvailable: Boolean,
    onPreviousStopClick: () -> Unit,
    isNextStopAvailable: Boolean,
    onNextStopClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            modifier = Modifier.alpha(if (isPreviousStopAvailable) 1f else 0f),
            onClick = onPreviousStopClick
        ) {
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
            modifier = Modifier.alpha(if (isNextStopAvailable) 1f else 0f),
            onClick = onNextStopClick
        ) {
            Icon(
                modifier = Modifier
                    .size(BrandTheme.sizing.icon),
                painter = painterResource(R.drawable.ic_arrow_forward),
                tint = BrandTheme.colorScheme.onBackground,
                contentDescription = stringResource(R.string.edit_trip_cd_next_stop)
            )
        }
    }
}

@Preview
@Composable
private fun EditTripTopBarBothAvailablePreview() {
    PreviewTheme {
        EditTripTopBar(
            isPreviousStopAvailable = true,
            onPreviousStopClick = {},
            isNextStopAvailable = true,
            onNextStopClick = {}
        )
    }
}

@Preview
@Composable
private fun EditTripTopBarNoneAvailablePreview() {
    PreviewTheme {
        EditTripTopBar(
            isPreviousStopAvailable = false,
            onPreviousStopClick = {},
            isNextStopAvailable = false,
            onNextStopClick = {}
        )
    }
}

@Preview
@Composable
private fun EditStopPreview() {
    PreviewTheme {
        EditStop(
            stop = EditTripStopItem(
                locationName = "London",
                arrivalDateStr = "21 Jun 2023",
                departureDateStr = "24 Jun 2023",
                latitude = 51.5073509,
                longitude = -0.1277583,
                country = Country(Country.Id("GB"), "United Kingdom", R.drawable.ic_single_stop)
            ),
            tripName = "Trip to London",
            predictions = listOf(),
            placeErrorText = null,
            showConfirmAddressButton = false,
            showExtraFields = true,
            isSavingTripEnabled = true,
            isAddressEditEnabled = true,
            placeTextChangedByUser = false,
            multiStop = false,
            onSearchTextChange = {},
            onBackEditingAddress = {},
            onConfirmAddress = {},
            onAddressSelect = {},
            onTripNameChange = {},
            onArrivalDateChange = { _, _, _ -> },
            onDepartureDateChange = { _, _, _ -> },
            onSaveChangesClick = {},
            onTripRemoveClick = {}
        )
    }
}

@Composable
fun EditStop(
    stop: EditTripStopItem,
    tripName: String,
    predictions: List<AddressPrediction>,
    placeErrorText: String?,
    showConfirmAddressButton: Boolean,
    showExtraFields: Boolean,
    isSavingTripEnabled: Boolean,
    isAddressEditEnabled: Boolean,
    placeTextChangedByUser: Boolean,
    multiStop: Boolean,
    onSearchTextChange: (String) -> Unit,
    onBackEditingAddress: () -> Unit,
    onConfirmAddress: () -> Unit,
    onAddressSelect: (AddressPrediction) -> Unit,
    onTripNameChange: (String) -> Unit,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onSaveChangesClick: () -> Unit,
    onTripRemoveClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = BrandTheme.spacing.screenPadding,
                end = BrandTheme.spacing.screenPadding,
                bottom = BrandTheme.spacing.screenPadding
            )
    ) {
        TripAddCard(
            placeText = stop.locationName,
            placeErrorText = placeErrorText,
            onSearchTextChange = onSearchTextChange,
            onBackClick = onBackEditingAddress,
            showBackArrow = true,
            onConfirmClick = onConfirmAddress,
            showConfirm = showConfirmAddressButton,
            expandDropdown = predictions.isNotEmpty() && placeTextChangedByUser,
            showExtraEditionFields = showExtraFields,
            predictions = predictions,
            onAddressPredictionClick = onAddressSelect,
            nameText = tripName,
            onNameTextChange = onTripNameChange,
            arrivalDate = stop.arrivalDateStr,
            onArrivalDateChange = onArrivalDateChange,
            departureDate = stop.departureDateStr,
            onDepartureDateChange = onDepartureDateChange,
            onPositiveClick = onSaveChangesClick,
            positiveClickText = stringResource(R.string.edit_trip_btn_save_changes),
            onNegativeClick = onTripRemoveClick,
            negativeClickText = stringResource(R.string.edit_trip_btn_delete),
            onMiddleClick = null,
            middleClickText = null,
            isSavingEnabled = isSavingTripEnabled,
            isAddressEditEnabled = isAddressEditEnabled,
            multiStop = multiStop
        )

        Spacer(Modifier.height(BrandTheme.spacing.cardMargin))

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

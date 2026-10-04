package com.arturborowy.pins.screen.map

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.AddressPrediction
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.ui.composable.PreviewLightAndDark
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.composable.PrimaryColorCircularProgressIndicator
import com.arturborowy.pins.ui.composable.map.AllTripsMap
import com.arturborowy.pins.ui.composable.map.SelectedPlaceMap
import com.arturborowy.pins.ui.slideInFromBottom
import com.arturborowy.pins.ui.slideInFromTop
import com.arturborowy.pins.ui.slideOutToBottom
import com.arturborowy.pins.ui.slideOutToTop
import com.arturborowy.pins.utils.ScreenshotTest
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.showShortToast

@Composable
fun MapScreen(viewModel: MapViewModel = mapViewModel(false)) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorEvents.collect { showShortToast(context, it) }
    }

    MapView(
        marker = state.marker,
        tripMarkers = state.tripMarkers,
        addingTripStep = state.addingTripStep,
        placeText = state.placeText,
        placeErrorText = state.placeErrorText,
        showConfirmAddressButton = state.showConfirmAddressButton,
        predictions = state.predictions,
        placeTextChangedByUser = state.placeTextChangedByUser,
        showExtraFields = state.showExtraFields,
        nameText = state.nameText,
        arrivalDate = state.arrivalDate,
        departureDate = state.departureDate,
        isSavingTripEnabled = state.isSavingTripEnabled,
        isAddressEditEnabled = state.isAddressEditEnabled,
        multiStop = state.multiStop,
        onAddressSearchTextChange = { viewModel.onAddressSearchTextChange(it) },
        onBackEditingAddress = { viewModel.onBackEditingAddress() },
        onConfirmAddress = { viewModel.onConfirmAddress() },
        onAddressPredictionClick = { viewModel.onAddressSelect(it.id) },
        onTripNameChange = { viewModel.onTripNameChange(it) },
        onArrivalDateChange = { year, month, day ->
            viewModel.onArrivalDateChange(year, month, day)
        },
        onDepartureDateChange = { year, month, day ->
            viewModel.onDepartureDateChange(year, month, day)
        },
        onTripConfirmClick = { viewModel.onTripConfirmClick() },
        onTripCancelClick = { viewModel.onTripCancelClick() },
        onAddNextStopClick = { viewModel.onAddNextStopClick() },
        onAddTripClick = { viewModel.onAddTripClick() },
        onAddSingleStopTripClick = { viewModel.onAddSingleStopTripClick() },
        onAddMultiStopTripClick = { viewModel.onAddMultiStopTripClick() },
    )
}

@Composable
private fun MapView(
    marker: MapMarkerItem?,
    tripMarkers: List<List<MapMarkerItem>>,
    addingTripStep: AddingTripStep,
    placeText: String,
    placeErrorText: String?,
    showConfirmAddressButton: Boolean,
    predictions: List<AddressPrediction>,
    placeTextChangedByUser: Boolean,
    showExtraFields: Boolean,
    nameText: String,
    arrivalDate: String?,
    departureDate: String?,
    isSavingTripEnabled: Boolean,
    isAddressEditEnabled: Boolean,
    multiStop: Boolean,
    onAddressSearchTextChange: (String) -> Unit,
    onBackEditingAddress: () -> Unit,
    onConfirmAddress: () -> Unit,
    onAddressPredictionClick: (AddressPrediction) -> Unit,
    onTripNameChange: (String) -> Unit,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onTripConfirmClick: () -> Unit,
    onTripCancelClick: () -> Unit,
    onAddNextStopClick: () -> Unit,
    onAddTripClick: () -> Unit,
    onAddSingleStopTripClick: () -> Unit,
    onAddMultiStopTripClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background),
    ) {
        PrimaryColorCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        if (marker == null) {
            AllTripsMap(tripMarkers)
        } else {
            SelectedPlaceMap(marker)
        }

        MapScreenOverlays(
            addingTripStep = addingTripStep,
            placeText = placeText,
            placeErrorText = placeErrorText,
            showConfirmAddressButton = showConfirmAddressButton,
            predictions = predictions,
            placeTextChangedByUser = placeTextChangedByUser,
            showExtraFields = showExtraFields,
            nameText = nameText,
            arrivalDate = arrivalDate,
            departureDate = departureDate,
            isSavingTripEnabled = isSavingTripEnabled,
            isAddressEditEnabled = isAddressEditEnabled,
            multiStop = multiStop,
            onAddressSearchTextChange = onAddressSearchTextChange,
            onBackEditingAddress = onBackEditingAddress,
            onConfirmAddress = onConfirmAddress,
            onAddressPredictionClick = onAddressPredictionClick,
            onTripNameChange = onTripNameChange,
            onArrivalDateChange = onArrivalDateChange,
            onDepartureDateChange = onDepartureDateChange,
            onTripConfirmClick = onTripConfirmClick,
            onTripCancelClick = onTripCancelClick,
            onAddNextStopClick = onAddNextStopClick,
            onAddTripClick = onAddTripClick,
            onAddSingleStopTripClick = onAddSingleStopTripClick,
            onAddMultiStopTripClick = onAddMultiStopTripClick,
        )
    }
}

@Composable
private fun MapScreenOverlays(
    addingTripStep: AddingTripStep,
    placeText: String,
    placeErrorText: String?,
    showConfirmAddressButton: Boolean,
    predictions: List<AddressPrediction>,
    placeTextChangedByUser: Boolean,
    showExtraFields: Boolean,
    nameText: String,
    arrivalDate: String?,
    departureDate: String?,
    isSavingTripEnabled: Boolean,
    isAddressEditEnabled: Boolean,
    multiStop: Boolean,
    onAddressSearchTextChange: (String) -> Unit,
    onBackEditingAddress: () -> Unit,
    onConfirmAddress: () -> Unit,
    onAddressPredictionClick: (AddressPrediction) -> Unit,
    onTripNameChange: (String) -> Unit,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onTripConfirmClick: () -> Unit,
    onTripCancelClick: () -> Unit,
    onAddNextStopClick: () -> Unit,
    onAddTripClick: () -> Unit,
    onAddSingleStopTripClick: () -> Unit,
    onAddMultiStopTripClick: () -> Unit,
) {
    AnimatedContent(
        targetState = addingTripStep,
        transitionSpec = { overlayTransition },
    ) { currentOverlayType ->
        when (currentOverlayType) {
            AddingTripStep.FORM -> {
                TripAddOverlay(
                    placeText = placeText,
                    placeErrorText = placeErrorText,
                    onSearchTextChange = onAddressSearchTextChange,
                    showConfirm = showConfirmAddressButton,
                    expandDropdown = predictions.isNotEmpty() && placeTextChangedByUser,
                    showExtraFields = showExtraFields,
                    predictions = predictions,
                    nameText = nameText,
                    arrivalDate = arrivalDate,
                    departureDate = departureDate,
                    isSavingEnabled = isSavingTripEnabled,
                    isAddressEditEnabled = isAddressEditEnabled,
                    multiStop = multiStop,
                    onBackClick = onBackEditingAddress,
                    onConfirmClick = onConfirmAddress,
                    onAddressPredictionClick = onAddressPredictionClick,
                    onNameTextChange = onTripNameChange,
                    onArrivalDateChange = onArrivalDateChange,
                    onDepartureDateChange = onDepartureDateChange,
                    onPositiveClick = onTripConfirmClick,
                    onNegativeClick = onTripCancelClick,
                    onMiddleClick = onAddNextStopClick,
                )
            }

            AddingTripStep.ADD_TRIP_BUTTON -> {
                Box(Modifier.fillMaxSize()) {
                    Fab(
                        R.drawable.ic_add_trip,
                        R.string.main_bottom_nav_label_add,
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(BrandTheme.spacing.fabMargin),
                    ) { onAddTripClick() }
                }
            }

            AddingTripStep.TRIP_TYPE_BAR -> {
                Box(Modifier.fillMaxSize()) {
                    AddTripTypesBar(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(BrandTheme.spacing.fabMargin),
                        { onAddSingleStopTripClick() },
                        { onAddMultiStopTripClick() },
                    )
                }
            }
        }
    }
}

private val AnimatedContentTransitionScope<AddingTripStep>.overlayTransition
    get() = when {
        targetState == AddingTripStep.ADD_TRIP_BUTTON && initialState == AddingTripStep.FORM ->
            slideInFromBottom togetherWith slideOutToBottom

        targetState == AddingTripStep.FORM || initialState == AddingTripStep.FORM
            -> slideInFromTop togetherWith slideOutToTop

        else -> slideInFromBottom togetherWith slideOutToTop
    }.using(
        SizeTransform(clip = false),
    )

@PreviewLightAndDark
@ScreenshotTest
@Composable
private fun MapViewPreview() = PreviewTheme {
    MapView(
        marker = null,
        tripMarkers = emptyList(),
        addingTripStep = AddingTripStep.ADD_TRIP_BUTTON,
        placeText = "",
        placeErrorText = null,
        showConfirmAddressButton = false,
        predictions = emptyList(),
        placeTextChangedByUser = false,
        showExtraFields = false,
        nameText = "",
        arrivalDate = null,
        departureDate = null,
        isSavingTripEnabled = false,
        isAddressEditEnabled = false,
        multiStop = false,
        onAddressSearchTextChange = {},
        onBackEditingAddress = {},
        onConfirmAddress = {},
        onAddressPredictionClick = {},
        onTripNameChange = {},
        onArrivalDateChange = { _, _, _ -> },
        onDepartureDateChange = { _, _, _ -> },
        onTripConfirmClick = {},
        onTripCancelClick = {},
        onAddNextStopClick = {},
        onAddTripClick = {},
        onAddSingleStopTripClick = {},
        onAddMultiStopTripClick = {},
    )
}

@PreviewLightAndDark
@ScreenshotTest
@Composable
private fun MapViewTripTypeBarPreview() = PreviewTheme {
    MapView(
        marker = null,
        tripMarkers = emptyList(),
        addingTripStep = AddingTripStep.TRIP_TYPE_BAR,
        placeText = "",
        placeErrorText = null,
        showConfirmAddressButton = false,
        predictions = emptyList(),
        placeTextChangedByUser = false,
        showExtraFields = false,
        nameText = "",
        arrivalDate = null,
        departureDate = null,
        isSavingTripEnabled = false,
        isAddressEditEnabled = false,
        multiStop = false,
        onAddressSearchTextChange = {},
        onBackEditingAddress = {},
        onConfirmAddress = {},
        onAddressPredictionClick = {},
        onTripNameChange = {},
        onArrivalDateChange = { _, _, _ -> },
        onDepartureDateChange = { _, _, _ -> },
        onTripConfirmClick = {},
        onTripCancelClick = {},
        onAddNextStopClick = {},
        onAddTripClick = {},
        onAddSingleStopTripClick = {},
        onAddMultiStopTripClick = {},
    )
}

@PreviewLightAndDark
@ScreenshotTest
@Composable
private fun MapViewFormPreview() = PreviewTheme {
    MapView(
        marker = null,
        tripMarkers = emptyList(),
        addingTripStep = AddingTripStep.FORM,
        placeText = "Paris, France",
        placeErrorText = null,
        showConfirmAddressButton = false,
        predictions = emptyList(),
        placeTextChangedByUser = false,
        showExtraFields = true,
        nameText = "Summer Trip",
        arrivalDate = "12/07/2024",
        departureDate = "20/07/2024",
        isSavingTripEnabled = true,
        isAddressEditEnabled = true,
        multiStop = false,
        onAddressSearchTextChange = {},
        onBackEditingAddress = {},
        onConfirmAddress = {},
        onAddressPredictionClick = {},
        onTripNameChange = {},
        onArrivalDateChange = { _, _, _ -> },
        onDepartureDateChange = { _, _, _ -> },
        onTripConfirmClick = {},
        onTripCancelClick = {},
        onAddNextStopClick = {},
        onAddTripClick = {},
        onAddSingleStopTripClick = {},
        onAddMultiStopTripClick = {},
    )
}

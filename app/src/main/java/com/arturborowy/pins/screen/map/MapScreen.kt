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
import com.arturborowy.pins.ui.composable.tripcard.StackTransitionExample
import com.arturborowy.pins.ui.slideInFromBottom
import com.arturborowy.pins.ui.slideInFromTop
import com.arturborowy.pins.ui.slideOutToBottom
import com.arturborowy.pins.ui.slideOutToTop
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background)
    ) {
        StackTransitionExample()
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
                        { onAddMultiStopTripClick() })
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
        SizeTransform(clip = false)
    )

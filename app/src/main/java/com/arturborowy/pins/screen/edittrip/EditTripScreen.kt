package com.arturborowy.pins.screen.edittrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.SingleTripAddCard
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.mapIconBitmapDescriptor
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.pxToDp
import com.arturborowy.pins.utils.showShortToast
import com.arturborowy.pins.utils.statusBarHeightPx
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTripScreen(viewModel: EditTripViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    if (state.errorText != null) {
        showShortToast(LocalContext.current, state.errorText)
        setState(state.copy(errorText = null))
    }

    val keyboard = LocalSoftwareKeyboardController.current

    val androidStatusBarHeight = pxToDp(LocalContext.current.statusBarHeightPx ?: 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary))
            .padding(16.dp, androidStatusBarHeight + 16.dp, 16.dp, 16.dp)
    ) {
        PageTitle(
            text = stringResource(R.string.edit_trip_header),
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
        )

        SingleTripAddCard(
            placeText = state.placeText,
            placeErrorText = state.placeErrorText,
            onSearchTextChange = {
                setState(
                    state.copy(
                        placeText = it, placeTextChangedByUser = true
                    )
                )
            },
            onBackClick = { viewModel.onBackEditingAddress() },
            showBackArrow = true,
            onConfirmClick = { viewModel.onConfirmAddress() },
            showConfirm = state.showConfirmAddressButton,
            expandDropdown = state.expandAddressPredictions && state.placeTextChangedByUser,
            showExtraEditionFields = state.showExtraFields,
            predictions = state.predictions,
            onAddressPredictionClick = { viewModel.onAddressSelect(it.id) },
            nameText = state.nameText,
            onNameTextChange = { viewModel.onTripNameChange(it) },
            arrivalDate = state.arrivalDate,
            onArrivalDateChange = { year: Int, month: Int, dayOfMonth: Int ->
                viewModel.onArrivalDateChange(year, month, dayOfMonth)
            },
            departureDate = state.departureDate,
            onDepartureDateChange = { year: Int, month: Int, dayOfMonth: Int ->
                viewModel.onDepartureDateChange(year, month, dayOfMonth)
            },
            onPositiveClick = { viewModel.onSaveChangesClick() },
            positiveClickText = stringResource(R.string.edit_trip_btn_save_changes),
            onNegativeClick = { viewModel.onTripCancelClick() },
            negativeClickText = stringResource(R.string.edit_trip_btn_delete),
            keyboard = keyboard,
            isSavingEnabled = state.isSavingTripEnabled,
            isAddressEditEnabled = state.isAddressEditEnabled
        )
        WideCard(padding = PaddingValues(0.dp)) {
            if (state.placeLongitude != null && state.placeLatitude != null && state.placeCountryIcon != null) {
                val location = LatLng(state.placeLatitude, state.placeLongitude)

                val cameraPositionState =
                    CameraPositionState(CameraPosition.fromLatLngZoom(location, 10f))

                GoogleMap(cameraPositionState = cameraPositionState) {
                    Marker(
                        icon = mapIconBitmapDescriptor(
                            LocalContext.current,
                            state.placeCountryIcon
                        ),
                        state = MarkerState(location),
                        title = state.placeText
                    )
                }
            }
        }
    }
}

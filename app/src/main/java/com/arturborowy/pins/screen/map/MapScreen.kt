package com.arturborowy.pins.screen.map

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.ui.composable.CircularProgressBar
import com.arturborowy.pins.ui.composable.Fab
import com.arturborowy.pins.utils.addBorderToCircle
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.cropBitmapToCircle
import com.arturborowy.pins.utils.getBitmapFromVectorDrawable
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.pxToDp
import com.arturborowy.pins.utils.showShortToast
import com.arturborowy.pins.utils.statusBarHeightPx
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import java.util.Calendar

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    if (state.errorText != null) {
        showShortToast(LocalContext.current, state.errorText)
        setState(state.copy(errorText = null))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary))
    ) {
        CircularProgressBar(modifier = Modifier.align(Alignment.Center))

        if (state.placeLongitude != null && state.placeLatitude != null && state.placeCountryIcon != null) {
            val location = LatLng(state.placeLatitude, state.placeLongitude)

            val cameraPositionState =
                CameraPositionState(CameraPosition.fromLatLngZoom(location, 10f))

            GoogleMap(cameraPositionState = cameraPositionState) {
                Marker(
                    icon = mapIconBitmapDescriptor(LocalContext.current, state.placeCountryIcon),
                    state = MarkerState(location),
                    title = state.placeText
                )
            }
        } else {

            //todo maybe on start just show some random pin on camera, but less zoom when setting?
            GoogleMap {
                state.tripMarkers.forEach {
                    Marker(
                        icon = mapIconBitmapDescriptor(LocalContext.current, it.countryIconResId),
                        state = MarkerState(LatLng(it.latitude, it.longitude)),
                        title = it.label,
                    )
                }
            }
        }

        if (state.showAddressTextField) {
            SearchBar(placeText = state.placeText,
                onSearchTextChange = {
                    setState(
                        state.copy(
                            placeText = it, placeTextChangedByUser = true
                        )
                    )
                },
                onBackClick = { viewModel.onBackEditingAddress() },
                onConfirmClick = { viewModel.onConfirmAddress() },
                showConfirm = state.showConfirmAddressButton,
                expandDropdown = state.expandAddressPredictions && state.placeTextChangedByUser,
                showExtraEditionFields = state.areExtraFieldsVisible,
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
                onTripConfirmClick = { viewModel.onTripConfirmClick() }
            )
        } else {
            Fab(
                R.drawable.ic_add_pin,
                R.string.main_bottom_nav_label_add,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            ) { viewModel.onAddPinClick() }
        }
    }
}

@Composable
fun mapIconBitmapDescriptor(
    context: Context,
    @DrawableRes vectorResId: Int
): BitmapDescriptor {
    val bitmap = getBitmapFromVectorDrawable(context, vectorResId, 0.05f)
        .cropBitmapToCircle()
        .addBorderToCircle(5.dp.value, context.getColor(R.color.primary))
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@Composable
fun SearchBar(
    placeText: String,
    onSearchTextChange: (String) -> Unit,
    nameText: String,
    onNameTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    showConfirm: Boolean,
    expandDropdown: Boolean,
    showExtraEditionFields: Boolean,
    predictions: List<AddressPredictionDto>,
    onAddressPredictionClick: (AddressPredictionDto) -> Unit,
    arrivalDate: String?,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    departureDate: String?,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onTripConfirmClick: () -> Unit
) {
    val androidStatusBarHeight = pxToDp(LocalContext.current.statusBarHeightPx ?: 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp + androidStatusBarHeight, 8.dp, 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ) {
            SearchField(
                placeText = placeText,
                onTextChange = onSearchTextChange,
                onBackClick = onBackClick,
                onConfirmClick = onConfirmClick,
                showConfirm = showConfirm,
            )
            SearchResults(
                expandDropdown = expandDropdown,
                predictions = predictions,
                onAddressPredictionClick = onAddressPredictionClick
            )

            if (showExtraEditionFields) {
                ExtraFields(
                    nameText = nameText,
                    onNameTextChange = onNameTextChange,
                    arrivalDate = arrivalDate,
                    onArrivalDateChange = onArrivalDateChange,
                    departureDate = departureDate,
                    onDepartureDateChange = onDepartureDateChange,
                    onTripConfirmClick = onTripConfirmClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtraFields(
    nameText: String,
    onNameTextChange: (String) -> Unit,
    arrivalDate: String?,
    onArrivalDateChange: (Int, Int, Int) -> Unit,
    departureDate: String?,
    onDepartureDateChange: (Int, Int, Int) -> Unit,
    onTripConfirmClick: () -> Unit
) {
    TextField(
        colors = textFieldColors(),
        modifier = Modifier.fillMaxWidth(),
        value = nameText,
        singleLine = true,
        onValueChange = { onNameTextChange(it) },
        label = { Text(stringResource(R.string.add_pin_hint_trip_name)) },
    )
    DatePickingButton(
        label = arrivalDate ?: stringResource(R.string.add_pin_hint_arrival_date),
        onDateSelected = onArrivalDateChange
    )
    DatePickingButton(
        label = departureDate ?: stringResource(R.string.add_pin_hint_departure_date),
        onDateSelected = onDepartureDateChange
    )

    Button(onClick = { onTripConfirmClick() }) {
        Text(text = stringResource(R.string.create_trip_btn_confirm))
    }
}

@Composable
fun DatePickingButton(
    label: String,
    onDateSelected: (Int, Int, Int) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()


    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    TextButton(onClick = {
        val datePicker = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                onDateSelected(selectedYear, selectedMonth, selectedDayOfMonth)
            },
            year,
            month,
            dayOfMonth
        )

        datePicker.show()
    }) {
        Text(label)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun textFieldColors() = TextFieldDefaults.textFieldColors(
    containerColor = Color.White,
    textColor = Color.Black,
    placeholderColor = colorResource(R.color.primary),
    cursorColor = Color.Black,
    selectionColors = TextSelectionColors(
        colorResource(R.color.primary), colorResource(R.color.primary)
    ),
    focusedIndicatorColor = colorResource(R.color.primary),
    unfocusedIndicatorColor = colorResource(R.color.primary),
    focusedLabelColor = colorResource(R.color.primary),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    placeText: String,
    onTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    showConfirm: Boolean,
) {
    TextField(colors = textFieldColors(),
        modifier = Modifier.fillMaxWidth(),
        value = placeText,
        singleLine = true,
        onValueChange = { onTextChange(it) },
        label = { Text(stringResource(R.string.add_pin_hint_name)) },
        leadingIcon = {
            TextButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_editing),
                    tint = colorResource(R.color.primary),
                    contentDescription = stringResource(R.string.add_pin_cd_address_editing_back)
                )
            }
        },
        trailingIcon = {
            if (showConfirm) {
                TextButton(onClick = { onConfirmClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_done),
                        tint = colorResource(R.color.primary),
                        contentDescription = stringResource(R.string.add_pin_btn_confirm)
                    )
                }
            }
        })
}

@Composable
fun SearchResults(
    expandDropdown: Boolean,
    predictions: List<AddressPredictionDto>,
    onAddressPredictionClick: (AddressPredictionDto) -> Unit
) {
    DropdownMenu(modifier = Modifier.background(Color.White),
        expanded = expandDropdown,
        properties = PopupProperties(
            clippingEnabled = false,
            focusable = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = {}) {
        predictions.forEach { addressPrediction ->
            DropdownMenuItem(onClick = { onAddressPredictionClick(addressPrediction) }, text = {
                Text(
                    addressPrediction.label,
                    color = Color.Black,
                )
            })
        }
    }
}
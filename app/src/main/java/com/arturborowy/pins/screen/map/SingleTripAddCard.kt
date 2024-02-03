package com.arturborowy.pins.screen.map

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.arturborowy.pins.R
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.utils.pxToDp
import com.arturborowy.pins.utils.statusBarHeightPx
import java.util.Calendar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SingleTripAddCard(
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
    onTripConfirmClick: () -> Unit,
    onTripCancelClick: () -> Unit,
    keyboard: SoftwareKeyboardController?,
    isSavingEnabled: Boolean,
    isAddressEditEnabled: Boolean
) {
    val androidStatusBarHeight = pxToDp(LocalContext.current.statusBarHeightPx ?: 0)

    WideCard(
        modifier = Modifier
            .padding(8.dp, 8.dp + androidStatusBarHeight, 8.dp, 8.dp),
        padding = PaddingValues(0.dp)
    ) {
        SearchField(
            placeText = placeText,
            onTextChange = onSearchTextChange,
            onBackClick = onBackClick,
            onConfirmClick = onConfirmClick,
            showConfirm = showConfirm,
            keyboard = keyboard,
            isAddressEditEnabled = isAddressEditEnabled
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
                onTripConfirmClick = onTripConfirmClick,
                onTripCancelClick = onTripCancelClick,
                isSavingEnabled = isSavingEnabled,
            )
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
    onTripConfirmClick: () -> Unit,
    onTripCancelClick: () -> Unit,
    isSavingEnabled: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        OutlinedTextField(
            colors = outlinedTextFieldColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 8.dp),
            value = nameText,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = { onNameTextChange(it) },
            label = { Text(stringResource(R.string.add_pin_hint_trip_name)) },
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DatePickingButton(
                label = stringResource(R.string.add_pin_hint_arrival_date),
                date = arrivalDate,
                onDateSelected = onArrivalDateChange,
            )
            Spacer(modifier = Modifier.width(4.dp))
            DatePickingButton(
                label = stringResource(R.string.add_pin_hint_departure_date),
                date = departureDate,
                onDateSelected = onDepartureDateChange,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = colorResource(R.color.primary)
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(R.color.primary),
                ),
                onClick = { onTripCancelClick() }) {
                Text(text = stringResource(R.string.create_trip_btn_cancel))
            }
            Button(modifier = Modifier
                .weight(1f)
                .padding(8.dp),
                enabled = isSavingEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary)),
                onClick = { onTripConfirmClick() }) {
                Text(text = stringResource(R.string.create_trip_btn_confirm))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickingButton(
    label: String,
    date: String?,
    onDateSelected: (Int, Int, Int) -> Unit,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    OutlinedTextField(modifier = Modifier
        .widthIn(1.dp, 200.dp)
        .onFocusChanged {
            if (it.isFocused) {
                val datePicker = DatePickerDialog(
                    context,
                    R.style.PinsDialog,
                    { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                        onDateSelected(selectedYear, selectedMonth, selectedDayOfMonth)
                    },
                    year,
                    month,
                    dayOfMonth
                )

                datePicker.show()
            }
        },
        colors = outlinedTextFieldColors(),
        value = date ?: label,
        onValueChange = {},
        singleLine = true,
        readOnly = true,
        label = {
            if (date != null) {
                Text(
                    text = label
                )
            }
        },
        textStyle = TextStyle(fontSize = 13.sp),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_calendar),
                tint = colorResource(R.color.primary),
                contentDescription = label
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun outlinedTextFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    textColor = colorResource(R.color.primary),
    focusedBorderColor = colorResource(R.color.primary),
    unfocusedBorderColor = colorResource(R.color.primary),
    focusedLabelColor = colorResource(R.color.primary),
    unfocusedLabelColor = colorResource(R.color.primary),
)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    placeText: String,
    onTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: () -> Unit,
    showConfirm: Boolean,
    keyboard: SoftwareKeyboardController?,
    isAddressEditEnabled: Boolean
) {
    val windowInfo = LocalWindowInfo.current

    val focusRequester = remember { FocusRequester() }

    var isFocused = remember { false }

    OutlinedTextField(
        colors = outlinedTextFieldColors(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp, 8.dp, 8.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
            },
        value = placeText,
        singleLine = true,
        readOnly = isAddressEditEnabled.not(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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

    LaunchedEffect(focusRequester) {
        snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
            if (isWindowFocused && isFocused.not()) {
                focusRequester.requestFocus()
                keyboard?.show()
            }
        }
    }
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

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun Preview() {
    SingleTripAddCard(
        placeText = "Place text",
        onSearchTextChange = {},
        nameText = "Name text",
        onNameTextChange = {},
        onBackClick = { },
        onConfirmClick = { },
        showConfirm = true,
        expandDropdown = false,
        showExtraEditionFields = true,
        predictions = listOf(),
        onAddressPredictionClick = {},
        arrivalDate = stringResource(R.string.add_pin_hint_arrival_date),
        onArrivalDateChange = { i: Int, i1: Int, i2: Int -> },
        departureDate = stringResource(R.string.add_pin_hint_departure_date),
        onDepartureDateChange = { i: Int, i1: Int, i2: Int -> },
        onTripConfirmClick = {},
        onTripCancelClick = {},
        keyboard = null,
        isSavingEnabled = true,
        isAddressEditEnabled = true
    )
}

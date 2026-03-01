package com.arturborowy.pins.ui.composable

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.arturborowy.pins.R
import com.arturborowy.pins.model.remote.places.AddressPredictionDto
import com.arturborowy.pins.ui.theme.PinsTheme
import java.util.Calendar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TripAddCard(
    modifier: Modifier = Modifier,
    placeText: String,
    placeErrorText: String? = null,
    onSearchTextChange: (String) -> Unit,
    nameText: String,
    onNameTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    showBackArrow: Boolean,
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
    onPositiveClick: () -> Unit,
    positiveClickText: String,
    onNegativeClick: () -> Unit,
    negativeClickText: String,
    onMiddleClick: (() -> Unit)?,
    middleClickText: String?,
    keyboard: SoftwareKeyboardController?,
    isSavingEnabled: Boolean,
    isAddressEditEnabled: Boolean,
    multiStop: Boolean
) {
    WideCard(
        modifier = modifier,
        padding = PaddingValues(0.dp)
    ) {
        SearchField(
            placeText = placeText,
            errorText = placeErrorText,
            onTextChange = onSearchTextChange,
            onBackClick = onBackClick,
            showBackArrow = showBackArrow,
            onConfirmClick = onConfirmClick,
            showConfirm = showConfirm,
            keyboard = keyboard,
            isAddressEditEnabled = isAddressEditEnabled,
            showExtraEditionFields = showExtraEditionFields
        )
        SearchResults(
            expandDropdown = expandDropdown,
            predictions = predictions,
            onAddressPredictionClick = onAddressPredictionClick
        )

        if (showExtraEditionFields) {
            if (multiStop) {
                TripMultiStopExtraFields(
                    nameText = nameText,
                    onNameTextChange = onNameTextChange,
                    arrivalDate = arrivalDate,
                    onArrivalDateChange = onArrivalDateChange,
                    onPositiveClick = onPositiveClick,
                    positiveClickText = positiveClickText,
                    onNegativeClick = onNegativeClick,
                    negativeClickText = negativeClickText,
                    onMiddleClick = onMiddleClick,
                    middleClickText = middleClickText,
                    isSavingEnabled = isSavingEnabled,
                )
            } else {
                TripSingleStopExtraFields(
                    nameText = nameText,
                    onNameTextChange = onNameTextChange,
                    arrivalDate = arrivalDate,
                    onArrivalDateChange = onArrivalDateChange,
                    departureDate = departureDate,
                    onDepartureDateChange = onDepartureDateChange,
                    onPositiveClick = onPositiveClick,
                    positiveClickText = positiveClickText,
                    onNegativeClick = onNegativeClick,
                    negativeClickText = negativeClickText,
                    isSavingEnabled = isSavingEnabled,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripNameField(
    nameText: String,
    onNameTextChange: (String) -> Unit
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
        label = { Text(stringResource(R.string.add_trip_hint_trip_name)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickingButton(
    label: String,
    date: String?,
    onDateSelected: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val isDarkTheme = isSystemInDarkTheme()

    OutlinedTextField(
        modifier = modifier
            .padding(8.dp, 0.dp)
            .onFocusChanged {
                if (it.isFocused) {
                    val datePicker = DatePickerDialog(
                        context,
                        if (isDarkTheme) {
                            R.style.PinsDarkDialog
                        } else {
                            R.style.PinsLightDialog
                        },
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
        textStyle = PinsTheme.typography.bodyMedium,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_calendar),
                tint = PinsTheme.colorScheme.onBackground,
                contentDescription = label
            )
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun outlinedTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = PinsTheme.colorScheme.onBackground,
    unfocusedBorderColor = PinsTheme.colorScheme.onBackground,
    focusedTextColor = PinsTheme.colorScheme.onBackground,
    focusedLabelColor = PinsTheme.colorScheme.onBackground,
    unfocusedLabelColor = PinsTheme.colorScheme.onBackground,
)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    placeText: String,
    errorText: String? = null,
    onTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    showBackArrow: Boolean,
    onConfirmClick: () -> Unit,
    showConfirm: Boolean,
    keyboard: SoftwareKeyboardController?,
    isAddressEditEnabled: Boolean,
    showExtraEditionFields: Boolean,
) {
    val windowInfo = LocalWindowInfo.current

    val focusRequester = remember { FocusRequester() }

    var isFocused = remember { false }

    var textFieldValueState = TextFieldValue(placeText, TextRange(placeText.length))

    OutlinedTextField(
        colors = outlinedTextFieldColors(),
        isError = errorText != null && showExtraEditionFields.not(),
        supportingText = {
            if (errorText != null && showExtraEditionFields.not()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorText,
                    color = PinsTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp, 8.dp, 8.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
            },
        value = textFieldValueState,
        singleLine = true,
        readOnly = isAddressEditEnabled.not(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        onValueChange = {
            textFieldValueState = it
            onTextChange(it.text)
        },
        label = { Text(stringResource(R.string.add_trip_hint_name)) },
        leadingIcon = {
            if (showBackArrow) {
                TextButton(onClick = { onBackClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_editing),
                        tint = PinsTheme.colorScheme.onBackground,
                        contentDescription = stringResource(R.string.add_trip_cd_address_editing_back)
                    )
                }
            }
        },
        trailingIcon = {
            if (showConfirm) {
                TextButton(onClick = { onConfirmClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_done),
                        tint = PinsTheme.colorScheme.onBackground,
                        contentDescription = stringResource(R.string.add_trip_btn_confirm)
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
    DropdownMenu(
        modifier = Modifier.background(PinsTheme.colorScheme.surface),
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
                    color = PinsTheme.colorScheme.onSurface,
                )
            })
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun TripAddCardPreview() = PreviewTheme {
    TripAddCard(
        placeText = "Place text",
        placeErrorText = "No internet connection",
        onSearchTextChange = {},
        nameText = "Name text",
        onNameTextChange = {},
        onBackClick = { },
        showBackArrow = true,
        onConfirmClick = { },
        showConfirm = true,
        expandDropdown = false,
        showExtraEditionFields = true,
        predictions = listOf(),
        onAddressPredictionClick = {},
        arrivalDate = stringResource(R.string.add_trip_hint_arrival_date),
        onArrivalDateChange = { i: Int, i1: Int, i2: Int -> },
        departureDate = stringResource(R.string.add_trip_hint_departure_date),
        onDepartureDateChange = { i: Int, i1: Int, i2: Int -> },
        onPositiveClick = {},
        positiveClickText = "CONFIRM",
        onNegativeClick = {},
        middleClickText = "CONFIRM",
        onMiddleClick = {},
        negativeClickText = "CLOSE",
        keyboard = null,
        isSavingEnabled = true,
        isAddressEditEnabled = true,
        multiStop = true
    )
}

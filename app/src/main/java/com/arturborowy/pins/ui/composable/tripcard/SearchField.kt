package com.arturborowy.pins.ui.composable.tripcard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PinsOutlinedTextField
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.theme.PinsTheme

@OptIn(ExperimentalComposeUiApi::class)
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

    PinsOutlinedTextField(
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
            .padding(horizontal = 8.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused = it.isFocused
            },
        value = textFieldValueState,
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
                    TextFieldIcon(
                        R.drawable.ic_back_editing,
                        stringResource(R.string.add_trip_cd_address_editing_back)
                    )
                }
            }
        },
        trailingIcon = {
            if (showConfirm) {
                TextButton(onClick = { onConfirmClick() }) {
                    TextFieldIcon(
                        R.drawable.ic_done,
                        stringResource(R.string.add_trip_btn_confirm)
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
fun TextFieldIcon(@DrawableRes drawableResId: Int, contentDescription: String?) {
    Icon(
        painter = painterResource(drawableResId),
        tint = PinsTheme.colorScheme.onBackground,
        contentDescription = contentDescription
    )
}

@Preview
@Composable
fun SearchFieldPreview() = PreviewTheme {
    SearchField(
        placeText = "Lisbon, Portugal",
        onTextChange = {},
        onBackClick = {},
        showBackArrow = true,
        onConfirmClick = {},
        showConfirm = true,
        keyboard = null,
        isAddressEditEnabled = true,
        showExtraEditionFields = false
    )
}

@Preview
@Composable
fun SearchFieldErrorPreview() = PreviewTheme {
    SearchField(
        placeText = "",
        errorText = "No internet connection",
        onTextChange = {},
        onBackClick = {},
        showBackArrow = false,
        onConfirmClick = {},
        showConfirm = false,
        keyboard = null,
        isAddressEditEnabled = true,
        showExtraEditionFields = false
    )
}
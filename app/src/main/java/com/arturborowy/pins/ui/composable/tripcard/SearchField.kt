package com.arturborowy.pins.ui.composable.tripcard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PinsOutlinedTextField
import com.arturborowy.pins.ui.composable.PreviewTheme

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
    isAddressEditEnabled: Boolean,
    showExtraEditionFields: Boolean,
) {
    val windowInfo = LocalWindowInfo.current

    val focusRequester = remember { FocusRequester() }

    var isFocused by remember { mutableStateOf(false) }

    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                placeText,
                TextRange(placeText.length)
            )
        )
    }

    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(placeText) {
        if (textFieldValueState.text != placeText) {
            textFieldValueState = TextFieldValue(placeText, TextRange(placeText.length))
        }
    }

    PinsOutlinedTextField(
        isError = errorText != null && showExtraEditionFields.not(),
        supportingText = {
            if (errorText != null && showExtraEditionFields.not()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorText,
                    color = BrandTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
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
                TextButton(onClick = {
                    keyboard?.hide()
                    onConfirmClick()
                }) {
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
                withFrameMillis {}
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
        tint = BrandTheme.colorScheme.onBackground,
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
        isAddressEditEnabled = true,
        showExtraEditionFields = false
    )
}
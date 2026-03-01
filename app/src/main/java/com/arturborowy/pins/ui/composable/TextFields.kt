package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.arturborowy.pins.ui.theme.PinsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinsOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        leadingIcon = leadingIcon,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        textStyle = textStyle,
        colors = outlinedTextFieldColors(),
        singleLine = true,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinsOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    readOnly: Boolean = false,
    isError: Boolean = false,
    supportingText: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        readOnly = readOnly,
        isError = isError,
        supportingText = supportingText,
        keyboardOptions = keyboardOptions,
        colors = outlinedTextFieldColors(),
        singleLine = true,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun outlinedTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = PinsTheme.colorScheme.onBackground,
    unfocusedBorderColor = PinsTheme.colorScheme.onBackground,
    focusedTextColor = PinsTheme.colorScheme.onBackground,
    focusedLabelColor = PinsTheme.colorScheme.onBackground,
    unfocusedLabelColor = PinsTheme.colorScheme.onBackground,
)

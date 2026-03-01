package com.arturborowy.pins.ui.composable.tripcard

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.arturborowy.pins.ui.composable.PinsOutlinedTextField
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.ui.theme.PinsTheme
import java.util.Calendar

@Composable
fun DatePickingButton(
    label: String,
    date: String?,
    onDateSelected: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val isDarkTheme = isSystemInDarkTheme()

    PinsOutlinedTextField(
        modifier = modifier
            .padding(8.dp, 0.dp)
            .onFocusChanged {
                if (it.isFocused) {
                    showDatePicker(context, isDarkTheme, onDateSelected)
                }
            },
        value = date ?: label,
        onValueChange = {},
        readOnly = true,
        label = {
            if (date != null) {
                Text(text = label)
            }
        },
        textStyle = PinsTheme.typography.bodyMedium,
        leadingIcon = {
            TextFieldIcon(
                R.drawable.ic_calendar,
                label
            )
        })
}

private fun showDatePicker(
    context: Context,
    isDarkTheme: Boolean,
    onDateSelected: (Int, Int, Int) -> Unit
) {
    val calendar = Calendar.getInstance()

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

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

@Preview
@Composable
fun DatePickingButtonEmptyPreview() = PreviewTheme {
    DatePickingButton(
        label = "Arrival date",
        date = null,
        onDateSelected = { _, _, _ -> }
    )
}

@Preview
@Composable
fun DatePickingButtonFilledPreview() = PreviewTheme {
    DatePickingButton(
        label = "Arrival date",
        date = "01.01.2024",
        onDateSelected = { _, _, _ -> }
    )
}
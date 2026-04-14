package com.arturborowy.pins.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

val TextFieldValueSaver = Saver<TextFieldValue, Triple<String, Int, Int>>(
    save = { Triple(it.text, it.selection.start, it.selection.end) },
    restore = { (text, start, end) -> TextFieldValue(text, TextRange(start, end)) }
)

package com.arturborowy.pins.utils

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext


fun showShortToast(@ApplicationContext context: Context, text: String?) {
    showToast(context, text, Toast.LENGTH_SHORT)
}

fun showLongToast(@ApplicationContext context: Context, text: String?) {
    showToast(context, text, Toast.LENGTH_LONG)
}

private fun showToast(
    @ApplicationContext context: Context,
    text: String?,
    length: Int
) {
    Toast.makeText(context, text, length).show()
}

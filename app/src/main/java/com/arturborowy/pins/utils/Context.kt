package com.arturborowy.pins.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics

val Context.navigationBarHeightPx: Int?
    get() = if (hasSoftKeys)
        getSystemDimensionPx("navigation_bar_height")
    else {
        0
    }

val Context.statusBarHeightPx: Int?
    get() = getSystemDimensionPx("status_bar_height")

@SuppressLint("InternalInsetResource", "DiscouragedApi")
private fun Context.getSystemDimensionPx(name: String): Int? {
    val resourceId: Int = resources.getIdentifier(name, "dimen", "android")
    return if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else {
        null
    }
}

val Context.hasSoftKeys: Boolean
    get() {
        val realDisplayMetrics = DisplayMetrics()
        display?.getRealMetrics(realDisplayMetrics)
        val realHeight = realDisplayMetrics.heightPixels
        val realWidth = realDisplayMetrics.widthPixels
        val displayMetrics = DisplayMetrics()
        display?.getMetrics(displayMetrics)
        val displayHeight = displayMetrics.heightPixels
        val displayWidth = displayMetrics.widthPixels
        return realWidth - displayWidth > 0 || realHeight - displayHeight > 0
    }
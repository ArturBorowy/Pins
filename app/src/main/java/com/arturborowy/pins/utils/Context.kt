package com.arturborowy.pins.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.arturborowy.pins.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

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
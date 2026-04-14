package com.arturborowy.pins.utils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
fun mapIconBitmapDescriptor(
    context: Context,
    @DrawableRes vectorResId: Int
): BitmapDescriptor {
    val bitmap = getBitmapFromVectorDrawable(context, vectorResId, 0.05f)
        .cropBitmapToCircle()
        .addBorderToCircle(5.dp.value, Color(0xFF1B1282).toArgb())
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

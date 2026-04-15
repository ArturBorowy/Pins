package com.arturborowy.pins.utils

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.arturborowy.brand.designsystem.BrandTheme
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

private const val BITMAP_SCALE = 0.05f

@Composable
fun mapIconBitmapDescriptor(
    context: Context,
    @DrawableRes vectorResId: Int
): BitmapDescriptor {
    val bitmap = getBitmapFromVectorDrawable(context, vectorResId, BITMAP_SCALE)
        .cropBitmapToCircle()
        .addBorderToCircle(5.dp.value, BrandTheme.colorScheme.primary.toArgb())
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

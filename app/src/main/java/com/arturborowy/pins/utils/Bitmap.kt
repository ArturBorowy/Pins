package com.arturborowy.pins.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun bitmapDescriptor(
    context: Context,
    @DrawableRes vectorResId: Int
): BitmapDescriptor {
    val bitmap = getBitmapFromVectorDrawable(context, vectorResId, 0.05f)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun getBitmapFromVectorDrawable(
    context: Context,
    @DrawableRes drawableId: Int,
    scale: Float
): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)!!
    val bitmap = Bitmap.createBitmap(
        (drawable.intrinsicWidth.toFloat() * scale).toInt(),
        (drawable.intrinsicHeight.toFloat() * scale).toInt(),
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun Bitmap.cropBitmapToCircle(): Bitmap {
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    val rect = Rect(0, 0, width, height)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(
        (width / 2).toFloat(),
        (height / 2).toFloat(),
        (width / 2).toFloat(),
        paint
    )
    paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
    canvas.drawBitmap(this, rect, rect, paint)
    return output
}

fun Bitmap.addBorderToCircle(borderSize: Float, color: Int): Bitmap {
    val bmpWithBorder =
        Bitmap.createBitmap(
            width + borderSize.toInt() * 2,
            height + borderSize.toInt() * 2,
            config
        )
    val canvas = Canvas(bmpWithBorder)
    val paint = Paint()
    paint.color = color
    canvas.drawCircle(
        (bmpWithBorder.width / 2).toFloat(),
        (bmpWithBorder.height / 2).toFloat(),
        (bmpWithBorder.width / 2).toFloat(),
        paint
    )
    canvas.drawBitmap(this, borderSize, borderSize, null)
    return bmpWithBorder
}
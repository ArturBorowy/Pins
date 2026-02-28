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
import androidx.core.graphics.createBitmap

fun getBitmapFromVectorDrawable(
    context: Context,
    @DrawableRes drawableId: Int,
    scale: Float
): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)!!
    val bitmap = createBitmap(
        (drawable.intrinsicWidth.toFloat() * scale).toInt(),
        (drawable.intrinsicHeight.toFloat() * scale).toInt()
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun Bitmap.cropBitmapToCircle(): Bitmap {
    val output = createBitmap(width, height)
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
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    return output
}

fun Bitmap.addBorderToCircle(borderSize: Float, color: Int): Bitmap {
    val widthWithBorder = width + borderSize.toInt() * 2
    val heightWithBorder = height + borderSize.toInt() * 2

    val bmpWithBorder = if (config == null) {
        createBitmap(widthWithBorder, heightWithBorder)
    } else {
        createBitmap(widthWithBorder, heightWithBorder, config!!)
    }
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
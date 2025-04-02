package com.example.thrombolaize.main.helperclasses

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
fun hospitalMarkerIcon(@DrawableRes id: Int, scaleFactor: Float = 1.5f): BitmapDescriptor? {
    val context = LocalContext.current
    val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, context.theme) ?: return null
    val drawable = vectorDrawable.mutate()

    val width = (drawable.intrinsicWidth * scaleFactor).toInt()
    val height = (drawable.intrinsicHeight * scaleFactor).toInt()
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

package com.example.thrombolaize.ui.theme

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.LayoutDirection

val rectangleShape: Shape = GenericShape { size: Size, _: LayoutDirection ->
    moveTo(0f, 0f)
    lineTo(size.width, 0f)
    lineTo(size.width, size.height * 0.70f)
    lineTo(0f, size.height * 0.70f)
    close()
}
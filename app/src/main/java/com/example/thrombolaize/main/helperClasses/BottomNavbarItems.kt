package com.example.thrombolaize.main.helperClasses

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavIcon {
    data class VectorIcon(val imageVector: ImageVector) : NavIcon()
    data class PainterIcon(val painter: Painter) : NavIcon()
}

data class BottomNavbarItems(
    val title: String,
    val selectedItem: NavIcon,
    val unselectedItem: NavIcon,
    val hasBadge: Boolean,
    val hasBadgeWithCount: Int? = null
)
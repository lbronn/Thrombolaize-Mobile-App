package com.example.thrombolaize.main.dataClasses

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavbarItems (
    val title: String,
    val selectedItem: ImageVector,
    val unselectedItem: ImageVector,
    val hasBadge: Boolean,
    val hasBadgeWithCount: Int? = null
)
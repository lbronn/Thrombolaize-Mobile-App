package com.example.thrombolaize.main.helperclasses

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.thrombolaize.R
import com.example.thrombolaize.routes.Screens

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

@Composable
fun bottomNBIconsRoutes(): List<String> {
    return listOf(
        Screens.Home.route,
        Screens.Messages.route,
        Screens.Hospitals.route,
        Screens.Profile.route
    )
}
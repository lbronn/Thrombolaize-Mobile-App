package com.example.thrombolaize.view.modals

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.thrombolaize.R
import com.example.thrombolaize.main.helperclasses.BottomNavbarItems
import com.example.thrombolaize.main.helperclasses.NavIcon

@Composable
fun bottomNavBarIcons(): List<BottomNavbarItems> {
    return listOf(
        BottomNavbarItems(
            title = "Home",
            selectedItem = NavIcon.VectorIcon(Icons.Filled.Home),
            unselectedItem = NavIcon.VectorIcon(Icons.Outlined.Home),
            hasBadge = false
        ),
        BottomNavbarItems(
            title = "Messages",
            selectedItem = NavIcon.PainterIcon(painterResource(R.drawable.chats_vector)),
            unselectedItem = NavIcon.PainterIcon(painterResource(R.drawable.chats_outline_vector)),
            hasBadge = false,
            hasBadgeWithCount = 10
        ),
        BottomNavbarItems(
            title = "Hospitals",
            selectedItem = NavIcon.PainterIcon(painterResource(R.drawable.hospital_vector)),
            unselectedItem = NavIcon.PainterIcon(painterResource(R.drawable.hospital_outline_vector)),
            hasBadge = false
        ),
        BottomNavbarItems(
            title = "Profile",
            selectedItem = NavIcon.VectorIcon(Icons.Filled.Person),
            unselectedItem = NavIcon.VectorIcon(Icons.Outlined.Person),
            hasBadge = true
        )
    )
}
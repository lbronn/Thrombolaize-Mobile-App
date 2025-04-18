package com.example.thrombolaize.view.modals

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrombolaize.R
import com.example.thrombolaize.main.helperclasses.BottomNavbarItems
import com.example.thrombolaize.main.helperclasses.NavIcon
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily

@Composable
fun BottomNavBar(selectedIndex: Int, onItemSelected: (Int) -> Unit, items: List<BottomNavbarItems>, onFABClick: () -> Unit) {
    val extendedItems = if (items.size == 4) {
        listOf(items[0], items[1], null, items[2], items[3])
    } else {
        items.map { it }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 10.dp,
                    shape = RectangleShape,
                    ambientColor = DefaultShadowColor
                )
        ) {
            extendedItems.forEachIndexed { index, bottomNavbarItem ->
                if (bottomNavbarItem == null) {
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {},
                        label = {}
                    )
                } else {
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            onItemSelected(index)
                        },
                        label = {
                            Text(
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,
                                fontSize = 12.sp,
                                color = Color.Black,
                                text = bottomNavbarItem.title
                            )
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (bottomNavbarItem.hasBadgeWithCount != null) {
                                        Badge {
                                            Text(text = bottomNavbarItem.hasBadgeWithCount.toString())
                                        }
                                    } else if (bottomNavbarItem.hasBadge) {
                                        Badge()
                                    }
                                }
                            ) {
                                val icon = if (index == selectedIndex) {
                                    bottomNavbarItem.selectedItem
                                } else {
                                    bottomNavbarItem.unselectedItem
                                }

                                when (icon) {
                                    is NavIcon.VectorIcon -> {
                                        Icon(
                                            imageVector = icon.imageVector,
                                            contentDescription = bottomNavbarItem.title
                                        )
                                    }
                                    is NavIcon.PainterIcon -> {
                                        Icon(
                                            painter = icon.painter,
                                            contentDescription = bottomNavbarItem.title
                                        )
                                    }
                                }
                            }
                        },
                        colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                            selectedIconColor = FigmaBlue,
                            unselectedIconColor = FigmaBlue,
                            selectedTextColor = FigmaBlue,
                            unselectedTextColor = Color.Black,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onFABClick,
            containerColor = FigmaBlue,
            contentColor = White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 5.dp)
                .size(50.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.thrombo_button_vector),
                contentDescription = "Center FAB"
            )
        }
    }
}

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
            hasBadge = false
        )
    )
}
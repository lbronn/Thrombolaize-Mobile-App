package com.example.thrombolaize.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thrombolaize.main.dataClasses.BottomNavbarItems
import com.example.thrombolaize.routes.MainNavHost
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.Bubbles
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.ThrombolaizeTheme
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily

class Thrombolaize : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            ThrombolaizeTheme {
                val bottomNavBarItems = listOf(
                    BottomNavbarItems(
                        title = "Home",
                        selectedItem = Icons.Filled.Home,
                        unselectedItem = Icons.Outlined.Home,
                        hasBadge = false
                    ),
                    BottomNavbarItems(
                        title = "Messages",
                        selectedItem = Icons.AutoMirrored.Filled.Send,
                        unselectedItem = Icons.AutoMirrored.Outlined.Send,
                        hasBadge = false,
                        hasBadgeWithCount = 10
                    ),
                    BottomNavbarItems(
                        title = "Notifications",
                        selectedItem = Icons.Filled.Notifications,
                        unselectedItem = Icons.Outlined.Notifications,
                        hasBadge = false
                    ),
                    BottomNavbarItems(
                        title = "Profile",
                        selectedItem = Icons.Filled.Person,
                        unselectedItem = Icons.Outlined.Person,
                        hasBadge = true
                    )
                )

                var selectedBNBIndex by rememberSaveable { mutableIntStateOf(0) }
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val bottomNavRoutes = listOf(
                    Screens.Home.route,
                    Screens.Messages.route,
                    Screens.Notifications.route,
                    Screens.Profile.route
                )

                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Alabaster
                ) {
                    Scaffold(
                        bottomBar = {
                            if (currentRoute in bottomNavRoutes) {
                                NavigationBar(
                                    containerColor = FigmaBlue
                                ) {
                                    bottomNavBarItems.forEachIndexed { index, bottomNavbarItem ->
                                        NavigationBarItem(
                                            selected = selectedBNBIndex == index,
                                            onClick = {
                                                selectedBNBIndex = index
                                                when (index) {
                                                    0 -> navController.navigate(Screens.Home.route)
                                                    1 -> navController.navigate(Screens.Messages.route)
                                                    2 -> navController.navigate(Screens.Notifications.route)
                                                    3 -> navController.navigate(Screens.Profile.route)
                                                }
                                            },
                                            label = {
                                                Text(
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = fontFamily,
                                                    color = White,
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
                                                    Icon(
                                                        imageVector = if (index == selectedBNBIndex) {
                                                            bottomNavbarItem.selectedItem
                                                        } else {
                                                            bottomNavbarItem.unselectedItem
                                                        },
                                                        contentDescription = bottomNavbarItem.title
                                                    )
                                                }
                                            },
                                            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                                                selectedIconColor = FigmaBlue,
                                                unselectedIconColor = White,
                                                selectedTextColor = White,
                                                unselectedTextColor = White,
                                                indicatorColor = Bubbles
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    ) { innerPadding ->
                        MainNavHost(
                            navController = navController,
                            modifier = Modifier
                                .padding(innerPadding)
                                .background(Alabaster)
                        )
                    }
                }
            }
        }
    }
}
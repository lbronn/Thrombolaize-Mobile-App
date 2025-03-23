package com.example.thrombolaize.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thrombolaize.R
import com.example.thrombolaize.main.helperClasses.BottomNavbarItems
import com.example.thrombolaize.main.helperClasses.NavIcon
import com.example.thrombolaize.routes.MainNavHost
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.ThrombolaizeTheme
import com.example.thrombolaize.view.modals.BottomNavBar

class Thrombolaize : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ThrombolaizeTheme {
                val bottomNavBarItems = listOf(
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

                var selectedBNBIndex by rememberSaveable { mutableIntStateOf(0) }
                val navController = rememberNavController()
                val context = LocalContext.current

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val bottomNavRoutes = listOf(
                    Screens.Home.route,
                    Screens.Messages.route,
                    Screens.Hospitals.route,
                    Screens.Profile.route
                )

                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Alabaster
                ) {
                    Scaffold(
                        bottomBar = {
                            if (currentRoute in bottomNavRoutes) {
                                BottomNavBar(
                                    selectedIndex = selectedBNBIndex,
                                    onItemSelected = { index ->
                                        selectedBNBIndex = index
                                        when (index) {
                                            0 -> navController.navigate(Screens.Home.route)
                                            1 -> navController.navigate(Screens.Messages.route)
                                            3 -> navController.navigate(Screens.Hospitals.route)
                                            4 -> navController.navigate(Screens.Profile.route)
                                        }
                                    },
                                    items = bottomNavBarItems,
                                    onFABClick = {
                                        Toast.makeText(context, "Thrombolaize", Toast.LENGTH_SHORT).show()
                                    }
                                )
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
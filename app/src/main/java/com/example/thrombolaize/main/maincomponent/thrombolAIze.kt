package com.example.thrombolaize.main.maincomponent

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thrombolaize.main.helperclasses.bottomNBIconsRoutes
import com.example.thrombolaize.routes.MainNavHost
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.ThrombolaizeTheme
import com.example.thrombolaize.view.modals.BottomNavBar
import com.example.thrombolaize.view.modals.OpenThromboModalSheet
import com.example.thrombolaize.view.modals.bottomNavBarIcons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Thrombolaize : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ThrombolaizeTheme {
                val bottomNavBarItems = bottomNavBarIcons()
                var selectedBNBIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val bottomNavRoutes = bottomNBIconsRoutes()

                var showThrombolaizeModal by remember {
                    mutableStateOf(false)
                }

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
                                        showThrombolaizeModal = true
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

                    if (showThrombolaizeModal) {
                        OpenThromboModalSheet(
                            onDismissRequest = {
                                showThrombolaizeModal = false
                            },
                            navController
                        )
                    }
                }
            }
        }
    }
}
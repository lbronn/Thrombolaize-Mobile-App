package com.example.thrombolaize.main.maincomponent

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thrombolaize.main.helperclasses.bottomNBIconsRoutes
import com.example.thrombolaize.routes.MainNavHost
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.ThrombolaizeTheme
import com.example.thrombolaize.view.modals.BottomNavBar
import com.example.thrombolaize.view.modals.bottomNavBarIcons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Thrombolaize : ComponentActivity() {
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
                val context = LocalContext.current

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val bottomNavRoutes = bottomNBIconsRoutes()

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
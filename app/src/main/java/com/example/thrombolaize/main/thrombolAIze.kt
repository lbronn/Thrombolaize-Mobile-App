package com.example.thrombolaize.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.thrombolaize.ui.theme.ThrombolaizeTheme
import com.example.thrombolaize.routes.MainNavHost

class Thrombolaize : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge(
        installSplashScreen()
        setContent {
            ThrombolaizeTheme {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    MainNavHost(navController)
                }
            }
        }
    }
}
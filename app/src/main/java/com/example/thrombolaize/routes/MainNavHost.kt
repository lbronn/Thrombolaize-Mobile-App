package com.example.thrombolaize.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thrombolaize.view.Login
import com.example.thrombolaize.view.Register
import com.example.thrombolaize.view.ForgotPassword
import com.example.thrombolaize.view.Home
import com.example.thrombolaize.view.Messages
import com.example.thrombolaize.view.Notifications
import com.example.thrombolaize.view.Profile

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screens.Login.route, modifier = modifier) {
        composable(Screens.Login.route) {
            Login(
                loginSuccess = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                navController = navController
            )
        }

        composable(Screens.Register.route) {
            Register(
                registerSuccess = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                navController = navController
            )
        }

        composable(Screens.ForgotPassword.route) {
            ForgotPassword(
                navController = navController
            )
        }

        composable(Screens.Home.route) {
            Home()
        }

        composable(Screens.Messages.route) {
            Messages()
        }

        composable(Screens.Notifications.route) {
            Notifications()
        }

        composable(Screens.Profile.route) {
            Profile()
        }
    }
}

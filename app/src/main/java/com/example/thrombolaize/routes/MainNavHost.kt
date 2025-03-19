package com.example.thrombolaize.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thrombolaize.view.Login
import com.example.thrombolaize.view.Register
import com.example.thrombolaize.view.ForgotPassword

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.Login.route) {
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
                    navController.navigate(Screens.Home.route) {
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

//        composable(Screens.Home.route) {
//            Home()
//        }
    }
}

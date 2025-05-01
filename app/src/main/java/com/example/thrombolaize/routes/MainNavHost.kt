package com.example.thrombolaize.routes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thrombolaize.view.screens.Chats
import com.example.thrombolaize.view.screens.Login
import com.example.thrombolaize.view.screens.Register
import com.example.thrombolaize.view.screens.ForgotPassword
import com.example.thrombolaize.view.screens.Home
import com.example.thrombolaize.view.screens.Hospitals
import com.example.thrombolaize.view.screens.Messages
import com.example.thrombolaize.view.screens.Profile
import com.example.thrombolaize.view.screens.EditProfile
import com.example.thrombolaize.view.screens.ThrombolaizeResult

@RequiresApi(Build.VERSION_CODES.O)
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
            ForgotPassword(navController = navController)
        }

        composable(Screens.Home.route) {
            Home(navController = navController)
        }

        composable(Screens.Messages.route) {
            Messages(navController = navController)
        }

        composable(Screens.Hospitals.route) {
            Hospitals()
        }

        composable(Screens.Profile.route) {
            Profile(navController = navController)
        }

        composable(Screens.EditProfile.route) {
            EditProfile(navController = navController)
        }

        composable("chats/{messageID}/{receiverID}/{receiverName}", arguments = listOf(
            navArgument("messageID") { type = NavType.StringType },
            navArgument("receiverID") { type = NavType.StringType },
            navArgument("receiverName") { type = NavType.StringType }
        )) {
            val messageID = it.arguments?.getString("messageID") ?: ""
            val receiverID = it.arguments?.getString("receiverID") ?: ""
            val receiverName = it.arguments?.getString("receiverName") ?: ""
            Chats(navController = navController, messageID = messageID, receiverID = receiverID, receiverName = receiverName)
        }

        composable("thrombo_result/{createdAt}", arguments = listOf(
            navArgument("createdAt") { type = NavType.LongType }
        )) { backStackEntry ->
            val createdAtArg = backStackEntry.arguments!!.getLong("createdAt")
            ThrombolaizeResult(navController, createdAtArg)
        }
    }
}
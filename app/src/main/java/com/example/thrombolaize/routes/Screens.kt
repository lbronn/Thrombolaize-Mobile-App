package com.example.thrombolaize.routes

sealed class Screens(val route: String) {
    data object Login: Screens("login")
    data object Register: Screens("register")
    data object ForgotPassword: Screens("forgotPassword")
    data object Home: Screens("home")
}
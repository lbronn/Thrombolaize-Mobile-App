package com.example.thrombolaize.main.helperclasses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel

@Composable
fun UseLaunchEffect(userAuthenticateViewModel: UserAuthenticationViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        userAuthenticateViewModel.fetchLoggedInUser()
    }
}
package com.example.thrombolaize.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrombolaize.helperclasses.UseLaunchEffect
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel

@Composable
fun Home(userAuthenticateViewModel: UserAuthenticationViewModel = viewModel()) {
    UseLaunchEffect(userAuthenticateViewModel)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Alabaster),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            text = "Home Screen"
        )
    }
}
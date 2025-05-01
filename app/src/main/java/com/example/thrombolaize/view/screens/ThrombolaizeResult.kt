package com.example.thrombolaize.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.thrombolaize.ui.theme.Alabaster

@Composable
fun ThrombolaizeResult(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(Alabaster)
    ) {
        Text(
            text = "Thrombolaize Result"
        )
    }
}
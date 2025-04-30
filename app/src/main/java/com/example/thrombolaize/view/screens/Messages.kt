@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thrombolaize.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.main.helperclasses.MessagesInfo
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.view.modals.NewMessagesModalSheet

@Composable
fun Messages(navController: NavController) {
    var showAddMessageModal by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        text = "Messages",
                        color = White
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            showAddMessageModal = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.newchat_vector),
                            contentDescription = "new chat",
                            tint = White,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                },
                expandedHeight = 70.dp,
                colors = TopAppBarDefaults.topAppBarColors(FigmaBlue),
            )

            if (showAddMessageModal) {
                NewMessagesModalSheet (
                    onDismissRequest = {
                        showAddMessageModal = false
                    }
                )
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(Alabaster)
        ) {
            MessagesInfo(navController = navController)
        }
    }
}
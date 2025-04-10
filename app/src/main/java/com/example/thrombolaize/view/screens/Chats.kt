@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thrombolaize.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.main.helperclasses.ChatsInMessages
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.view.modals.DeleteChatModalSheet
import com.example.thrombolaize.viewmodel.ChatsViewModel

@Composable
fun Chats(navController: NavController, messageID: String, receiverID: String, receiverName: String) {
    val chatsViewModel: ChatsViewModel = hiltViewModel()
    val chats by chatsViewModel.currentChats.collectAsState()
    var showDeleteConversationModal by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        chatsViewModel.chatsListener(messageID)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 25.sp,
                        text = receiverName,
                        color = White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screens.Messages.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "back",
                            tint = White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            showDeleteConversationModal = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.moresettings_vector),
                            contentDescription = "more settings",
                            tint = White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(FigmaBlue)
            )

            if (showDeleteConversationModal) {
                DeleteChatModalSheet (
                    onDismissRequest = {
                        showDeleteConversationModal = false
                    },
                    navController,
                    messageID
                )
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(color = Alabaster)
        ) {
            ChatsInMessages(
                chats = chats,
                onSendChat = { chatContent ->
                    chatsViewModel.sendChats(messageID, chatContent, receiverID, receiverName)
                }
            )
        }
    }
}
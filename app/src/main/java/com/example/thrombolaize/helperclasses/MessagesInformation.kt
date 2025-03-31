package com.example.thrombolaize.helperclasses

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.thrombolaize.R
import com.example.thrombolaize.view.modals.MessagesCard
import com.example.thrombolaize.viewmodel.MessagesViewModel

@Composable
fun MessagesInfo() {
    val messagesViewModel = hiltViewModel<MessagesViewModel>()
    val messages by messagesViewModel.currentMessages.collectAsState()

    LazyColumn {
        items(messages) { message ->
            MessagesCard(
                painter = painterResource(R.drawable.person_vector),
                messageTitle = message.messageName
            )
        }
    }
}
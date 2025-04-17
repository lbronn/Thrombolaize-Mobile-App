package com.example.thrombolaize.main.helperclasses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.thrombolaize.model.Chat
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.view.modals.ChatBubble

@Composable
fun ChatsInMessages(chats: List<Chat>, onSendChat: (String) -> Unit) {
    val hideKeyboard = LocalSoftwareKeyboardController.current

    var chatContent by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            userScrollEnabled = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            items(chats) { chat ->
                ChatBubble(chat = chat)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(12.dp)
                .imePadding()
                .background(Alabaster)
        ) {
            OutlinedTextField(
                singleLine = true,
                value = chatContent,
                onValueChange = {
                    chatContent = it
                },
                placeholder = {
                    Text(text = "Message...")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedBorderColor = FigmaBlue,
                    unfocusedBorderColor = FigmaBlue,
                    focusedLeadingIconColor = FigmaBlue,
                    unfocusedLeadingIconColor = FigmaBlue,
                    focusedLabelColor = FigmaBlue,
                    unfocusedLabelColor = FigmaBlue,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                )
            )

            IconButton(
                onClick = {
                    onSendChat(chatContent)
                    chatContent = ""
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "send button",
                    tint = FigmaBlue,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}
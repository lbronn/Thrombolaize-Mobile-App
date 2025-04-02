package com.example.thrombolaize.view.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrombolaize.model.Chat
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ChatBubble(chat: Chat) {
    val isCurrentUser = chat.senderID == Firebase.auth.currentUser?.uid
    val chatBubbleColor = if (isCurrentUser) FigmaBlue else Color.LightGray

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 15.dp)
            .background(Alabaster)
    ) {
        val chatAlignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart

        Box(
            modifier = Modifier
                .padding(3.dp)
                .background(color = chatBubbleColor, shape = RoundedCornerShape(12.dp))
                .align(chatAlignment)
        ) {
            Text(
                fontFamily = fontFamily,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                text = chat.chatContent,
                color = if (isCurrentUser) White else Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
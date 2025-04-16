package com.example.thrombolaize.main.helperclasses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.view.modals.MessagesCard
import com.example.thrombolaize.viewmodel.MessagesViewModel
import com.example.thrombolaize.viewmodel.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MessagesInfo(navController: NavController) {
    val messagesViewModel = hiltViewModel<MessagesViewModel>()
    val userProfileViewModel: UserProfileViewModel = viewModel()

    val messages by messagesViewModel.currentMessages.collectAsState()
    val currentUser = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val allUserDetails by userProfileViewModel.currentUserDetails.collectAsState()

    LazyColumn {
        items(messages) { message ->
            val displayName: String
            val partnerID: String
            val partnerName: String

            if (message.senderID == currentUser) {
                displayName = message.receiverName
                partnerID = message.receiverID
                partnerName = message.receiverName
            } else {
                displayName = message.senderName
                partnerID = message.senderID
                partnerName = message.senderName
            }

            val partnerDetails = allUserDetails.firstOrNull { it.uid == partnerID }
            val profilePicURL = partnerDetails?.userProfilePicURL

            if (!profilePicURL.isNullOrEmpty()) {
                MessagesCard(
                    painter = null,
                    picture = profilePicURL,
                    messageTitle = displayName,
                    modifier = Modifier.clickable {
                        navController.navigate("chats/${message.messageID}/${partnerID}/${partnerName}")
                    },
                )
            } else {
                MessagesCard(
                    painter = painterResource(R.drawable.person_vector),
                    picture = "",
                    messageTitle = displayName,
                    modifier = Modifier.clickable {
                        navController.navigate("chats/${message.messageID}/${partnerID}/${partnerName}")
                    },
                )
            }
        }
    }
}
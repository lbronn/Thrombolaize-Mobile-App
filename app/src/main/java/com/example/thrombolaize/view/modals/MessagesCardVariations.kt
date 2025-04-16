package com.example.thrombolaize.view.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel
import com.example.thrombolaize.viewmodel.UserProfileViewModel

@Composable
fun MessagesCard(picture: String?, painter: Painter?, messageTitle: String, modifier: Modifier) {
    val userProfileViewModel: UserProfileViewModel = viewModel()
    val userAuthenticateViewModel: UserAuthenticationViewModel = viewModel()

    val user = userAuthenticateViewModel.currentUser
    val currentUserUID = user?.uid ?: ""
    val allUserDetails by userProfileViewModel.currentUserDetails.collectAsState()
    val currentUserProfile = allUserDetails.firstOrNull { it.uid == currentUserUID }
    val userProfilePictureURL = currentUserProfile?.userProfilePicURL

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 13.dp, vertical = 9.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardColors(
            containerColor = White,
            contentColor = White,
            disabledContainerColor = White,
            disabledContentColor = White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (painter != null) {
                    Icon(
                        painter = painter,
                        contentDescription = "icons",
                        tint = White,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(FigmaBlue)
                            .size(60.dp)
                            .padding(10.dp)
                    )
                } else {
                    if (!userProfilePictureURL.isNullOrEmpty()) {
                        AsyncImage(
                            model = picture,
                            contentDescription = "profile picture",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.Transparent)
                                .size(60.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.width(20.dp)
                )

                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    text = messageTitle,
                    color = FigmaBlue,
                    modifier = modifier
                )
            }
        }
    }
}
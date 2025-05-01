@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thrombolaize.view.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.thrombolaize.R
import com.example.thrombolaize.main.helperclasses.UseLaunchEffect
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.view.modals.CurrentTimeCard
import com.example.thrombolaize.view.modals.ThrombolaizeHistory
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel
import com.example.thrombolaize.viewmodel.UserProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    navController: NavController,
    userAuthenticateViewModel: UserAuthenticationViewModel = viewModel(),
    userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {
    UseLaunchEffect(userAuthenticateViewModel)
    val userName = userAuthenticateViewModel.currentUserName

    val user = userAuthenticateViewModel.currentUser
    val currentUserUID = user?.uid ?: ""
    val allUserDetails by userProfileViewModel.currentUserDetails.collectAsState()
    val currentUserProfile = allUserDetails.firstOrNull { it.uid == currentUserUID }
    val userProfilePictureURL = currentUserProfile?.userProfilePicURL

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(FigmaBlue)
                            .offset(x = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .size(65.dp)
                                .clip(CircleShape)
                        ) {
                            if (!userProfilePictureURL.isNullOrEmpty()) {
                                AsyncImage(
                                    model = userProfilePictureURL,
                                    contentDescription = "Profile Picture",
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(Color.Transparent)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.person_vector),
                                    tint = FigmaBlue,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(White)
                                        .size(120.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        Column {
                            Text(
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                color = White,
                                lineHeight = 20.sp,
                                text = "Welcome back, ",
                                modifier = Modifier.offset(y = 2.dp)
                            )

                            Text(
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                textAlign = TextAlign.Center,
                                color = White,
                                text = userName,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(FigmaBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(Alabaster)
        ) {
            CurrentTimeCard()

            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 125.dp)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    text = "Thrombolaized Patient Records",
                    color = FigmaBlue,
                )
            }

            ThrombolaizeHistory(navController)
        }
    }
}
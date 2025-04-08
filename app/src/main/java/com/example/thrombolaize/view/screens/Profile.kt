package com.example.thrombolaize.view.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.main.helperclasses.UseLaunchEffect
import com.example.thrombolaize.main.helperclasses.profileInfo
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.TopGradient
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.view.modals.LogoutBottomModalSheet
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel

@Composable
fun Profile(userAuthenticateViewModel: UserAuthenticationViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    val scrollable = rememberScrollState()

    var showBottomLogoutSheet by remember {
        mutableStateOf(false)
    }

    val user = userAuthenticateViewModel.currentUser
    val userName = userAuthenticateViewModel.currentUserName
    val displayName = "Dr. $userName"
    val displaySpecialty = user?.specialty ?: "No specialty found!"

    UseLaunchEffect(userAuthenticateViewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollable)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp)
                    .clip(RectangleShape)
                    .background(TopGradient)
            ) {
                IconButton (
                    onClick = {
                        Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(5.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.settings_vector),
                        contentDescription = "settings",
                        tint = White,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.Transparent)
                            .padding(7.dp)

                    )
                }
            }

            Box(
                modifier = Modifier
                    .offset(y = (-50).dp)
                    .clip(CircleShape)
                    .background(FigmaBlue)
                    .size(110.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.person_vector),
                    tint = White,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .padding(12.dp)
                        .size(120.dp)
                )
            }

            IconButton (
                onClick = {
                    navController.navigate(Screens.EditProfile.route)
                },
                modifier = Modifier.offset(x = 40.dp, y = -(89).dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit_vector),
                    contentDescription = "edit profile",
                    tint = FigmaBlue,
                    modifier = Modifier
                        .border(width = 2.dp, color = FigmaBlue, shape = CircleShape)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(White)
                        .padding(6.dp)
                )
            }

            Text(
                text = displayName,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .offset(y = (-85).dp)
            )
            Text(
                text = displaySpecialty,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                color = FigmaBlue,
                modifier = Modifier.offset(y = (-85).dp)
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            profileInfo()

            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .offset(y = -(45).dp),
                thickness = 2.dp,
                color = FigmaBlue
            )

            Column(
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        showBottomLogoutSheet = true
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    border = BorderStroke(3.dp, Color.Red),
                    contentPadding = PaddingValues(
                        start = 90.dp,
                        end = 90.dp,
                        top = 15.dp,
                        bottom = 15.dp
                    ),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .offset(y = (-15).dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        text = "Logout",
                        color = White
                    )
                }
            }

            if (showBottomLogoutSheet) {
                LogoutBottomModalSheet (
                    onDismissRequest = {
                        showBottomLogoutSheet = false
                    },
                    navController = navController
                )
            }
        }
    }
}
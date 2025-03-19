package com.example.thrombolaize.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.BleuDeFrance
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPassword(forgotPasswordViewModel: ForgotPasswordViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    var email by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "register image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        )

        Box(
            modifier = Modifier
                .size(width = 420.dp, height = 400.dp)
                .background(color = Color.Transparent)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(bottom = 0.dp)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    text = "Forgot Your Password?",
                    color = BleuDeFrance,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp)
                )

                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 25.sp,
                    fontSize = 14.sp,
                    text = "Enter your email address to reset it!",
                    color = BleuDeFrance,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            fontFamily = fontFamily,
                            text = "Email"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "email icon"
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = BleuDeFrance,
                        unfocusedBorderColor = BleuDeFrance,
                        focusedLeadingIconColor = BleuDeFrance,
                        unfocusedLeadingIconColor = BleuDeFrance,
                        focusedLabelColor = BleuDeFrance,
                        unfocusedLabelColor = BleuDeFrance,
                        unfocusedPlaceholderColor = Color.Gray,
                        focusedTextColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
                )

                Button(
                    onClick = {
                        forgotPasswordViewModel.forgotPassword(email) { success, errorMessage ->
                            if (success) {
                                Toast.makeText(context, "Email Sent!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, errorMessage ?: "Email is not registered.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(BleuDeFrance),
                    contentPadding = PaddingValues(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 80.dp, top = 12.dp, bottom = 5.dp)
                ) {
                    Text(
                        fontFamily = fontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Send to Email",
                        color = Color.White
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(Screens.Login.route)
                    },
                    colors = ButtonDefaults.buttonColors(Color.White),
                    border = BorderStroke(2.dp, color = BleuDeFrance),
                    contentPadding = PaddingValues(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 80.dp, end = 80.dp, top = 12.dp, bottom = 5.dp)
                ) {
                    Text(
                        fontFamily = fontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        text = "Back to Login",
                        color = BleuDeFrance
                    )
                }
            }
        }
    }
}
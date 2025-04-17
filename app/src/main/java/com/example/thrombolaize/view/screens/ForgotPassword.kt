package com.example.thrombolaize.view.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPassword(
    forgotPasswordViewModel: ForgotPasswordViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val hideKeyboard = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "register image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier.height(450.dp)
            )

            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                text = "Forgot Your Password?",
                color = FigmaBlue,
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
                color = FigmaBlue,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            )

            OutlinedTextField(
                singleLine = true,
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
                    focusedContainerColor = Alabaster,
                    unfocusedContainerColor = Alabaster,
                    focusedBorderColor = FigmaBlue,
                    unfocusedBorderColor = FigmaBlue,
                    focusedLeadingIconColor = FigmaBlue,
                    unfocusedLeadingIconColor = FigmaBlue,
                    focusedLabelColor = FigmaBlue,
                    unfocusedLabelColor = FigmaBlue,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                )
            )

            Button(
                onClick = {
                    forgotPasswordViewModel.forgotPassword(email) { success, error ->
                        if (success) {
                            Toast.makeText(context, "Email Sent!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, error ?: "Email is not registered.", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(FigmaBlue),
                border = BorderStroke(3.dp, FigmaBlue),
                contentPadding = PaddingValues(start = 90.dp, end = 90.dp, top = 15.dp, bottom = 15.dp),
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Send to Email",
                    color = Color.White
                )
            }

            TextButton(
                onClick = {
                    navController.navigate(Screens.Login.route)
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
                    .padding(bottom = 50.dp)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    text = "Just Remembered It? ",
                    color = Color.Black
                )

                Text(
                    fontFamily = fontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    text = "Log In",
                    color = FigmaBlue
                )
            }
        }
    }
}
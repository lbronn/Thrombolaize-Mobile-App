package com.example.thrombolaize.view.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel

@Composable
fun Login(loginSuccess: () -> Unit, userAuthenticateViewModel: UserAuthenticationViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordEntered by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.authentication_logo),
            contentDescription = "login image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 75.dp)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
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
                .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordEntered = true
            },
            label = {
                Text(
                    fontFamily = fontFamily,
                    text = "Password"
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "password icon"
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
                .padding(start = 30.dp, end = 30.dp),
            visualTransformation = if(!passwordEntered) VisualTransformation.None else PasswordVisualTransformation()
        )

        Button(
            onClick = {
                navController.navigate(Screens.ForgotPassword.route)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .padding(bottom = 10.dp, end = 10.dp)
                .align(Alignment.End)
        ) {
            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                text = "Forgot Password?",
                color = FigmaBlue
            )
        }

        Button(
            onClick = {
                userAuthenticateViewModel.loginUser(email, password) { success, errorMessage ->
                    if (success) {
                        loginSuccess()
                    } else {
                        Toast.makeText(context, errorMessage ?: "Invalid Credentials!", Toast.LENGTH_SHORT).show()
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
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                text = "Log In",
                color = White
            )
        }

        Button(
            onClick = {
                navController.navigate(Screens.Register.route)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .padding(bottom = 30.dp)
        ) {
            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                text = "Don't have an account? ",
                color = Color.Black
            )

            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                text = "Sign Up",
                color = FigmaBlue
            )
        }
    }
}
package com.example.thrombolaize.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.BleuDeFrance
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.LoginViewModel

@Composable
fun Login(loginSuccess: () -> Unit, loginViewModel: LoginViewModel = viewModel(), navController: NavController) {
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
                .padding(top = 100.dp)
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
                    imageVector = Icons.Default.Person,
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
                .padding(start = 30.dp, end = 30.dp, bottom = 10.dp),
            visualTransformation = if(!passwordEntered) VisualTransformation.None else PasswordVisualTransformation()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    loginViewModel.loginUser(email, password) { success, errorMessage ->
                        if (success) {
                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                            loginSuccess()
                        } else {
                            Toast.makeText(context, errorMessage ?: "Invalid Credentials!", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(BleuDeFrance),
                contentPadding = PaddingValues(start = 40.dp, end = 40.dp, top = 15.dp, bottom = 15.dp),
                modifier = Modifier.padding(top = 15.dp, start = 15.dp)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Login",
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    navController.navigate(Screens.Register.route)
                },
                colors = ButtonDefaults.buttonColors(White),
                border = BorderStroke(2.dp, BleuDeFrance),
                contentPadding = PaddingValues(start = 40.dp, end = 40.dp, top = 15.dp, bottom = 15.dp),
                modifier = Modifier.padding(top = 15.dp, end = 15.dp)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Register",
                    color = BleuDeFrance
                )
            }
        }

        Button(
            onClick = {
                navController.navigate(Screens.ForgotPassword.route)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .padding(top = 10.dp, bottom = 15.dp)
        ) {
            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.W900,
                fontSize = 15.sp,
                text = "Forgot Password? Click Here!",
                color = BleuDeFrance
            )
        }
    }
}
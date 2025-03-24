package com.example.thrombolaize.view.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel

@Composable
fun Register(registerSuccess: () -> Unit, userAuthenticateViewModel: UserAuthenticationViewModel = viewModel(), navController: NavController) {
    val context = LocalContext.current
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var specialty by remember {
        mutableStateOf("")
    }
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
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "register image",
            modifier = Modifier
                .fillMaxWidth()
                .size(350.dp, 350.dp)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(
                    fontFamily = fontFamily,
                    text = "First Name"
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "first name icon"
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
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text(
                    fontFamily = fontFamily,
                    text = "Last Name"
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "last name icon"
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
            value = specialty,
            onValueChange = { specialty = it },
            label = {
                Text(
                    fontFamily = fontFamily,
                    text = "Medical Specialty"
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.specialty_vector),
                    contentDescription = "medical specialty icon"
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
            visualTransformation = if (!passwordEntered) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
        )

        Button(
            onClick = {
                userAuthenticateViewModel.registerUser(firstName, lastName, specialty, email, password) { success, errorMessage ->
                    if (success) {
                        Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
                        registerSuccess()
                    } else {
                        Toast.makeText(context, errorMessage ?: "Registration Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(FigmaBlue),
            border = BorderStroke(3.dp, FigmaBlue),
            contentPadding = PaddingValues(start = 90.dp, end = 90.dp, top = 15.dp, bottom = 15.dp),
            modifier = Modifier
                .padding(top = 15.dp)
        ) {
            Text(
                fontFamily = fontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                text = "Register",
                color = Color.White
            )
        }

        Button(
            onClick = {
                navController.navigate(Screens.Login.route)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .padding(bottom = 30.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    text = "Already have an account? ",
                    color = Color.Black
                )

                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    text = "Login Here!",
                    color = FigmaBlue
                )
            }
        }
    }
}
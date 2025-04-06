@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thrombolaize.view.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.TopGradient
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.view.modals.AddProfilePicture
import com.example.thrombolaize.viewmodel.UserProfileViewModel

@Composable
fun EditProfile(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val hideKeyboard = LocalSoftwareKeyboardController.current
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()

    var about by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var workSchedule by remember {
        mutableStateOf("")
    }
    var hospitals by remember {
        mutableStateOf("")
    }
    var affiliations by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 22.sp,
                        text = "Add Details to your Profile",
                        color = White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screens.Profile.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "back",
                            tint = White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                },
                expandedHeight = 75.dp,
                colors = TopAppBarDefaults.topAppBarColors(FigmaBlue)
            )
        }
    ) { contentPadding ->
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(Alabaster)
                .verticalScroll(scrollState)
        ) {

            AddProfilePicture()

            OutlinedTextField(
                value = about,
                onValueChange = { about = it},
                minLines = 1,
                maxLines = 5,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "About You"
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 35.dp, bottom = 10.dp)
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it},
                minLines = 1,
                maxLines = 1,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "Mobile Number"
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
            )

            OutlinedTextField(
                value = workSchedule,
                onValueChange = { workSchedule = it},
                minLines = 1,
                maxLines = 3,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "Work Schedule"
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
            )

            OutlinedTextField(
                value = hospitals,
                onValueChange = { hospitals = it},
                minLines = 1,
                maxLines = 1,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "Hospitals"
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
            )

            OutlinedTextField(
                value = affiliations,
                onValueChange = { affiliations = it},
                minLines = 1,
                maxLines = 1,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "Affiliations"
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
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
            )

            Button(
                onClick = {
                    userProfileViewModel.addUserDetails(
                        aboutUser = about,
                        phoneNumber = phoneNumber,
                        workSchedule = workSchedule,
                        hospitals = hospitals,
                        affiliations = affiliations
                    ) { success, message ->
                        if (success) {
                            Toast.makeText(context, "Profile saved successfully!", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.Profile.route)
                        } else {
                            Toast.makeText(context, message ?: "Failed to save profile.", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(FigmaBlue),
                border = BorderStroke(3.dp, FigmaBlue),
                contentPadding = PaddingValues(start = 90.dp, end = 90.dp, top = 15.dp, bottom = 15.dp),
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 30.dp)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Save Profile",
                    color = Color.White
                )
            }
        }
    }
}
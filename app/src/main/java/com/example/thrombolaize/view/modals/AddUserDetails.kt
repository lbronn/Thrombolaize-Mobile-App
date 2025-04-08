package com.example.thrombolaize.view.modals

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.UserProfileViewModel

@Composable
fun AddUserDetails(navController: NavController) {
    val context = LocalContext.current
    val hideKeyboard = LocalSoftwareKeyboardController.current
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()

    var about by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var workSchedule by remember { mutableStateOf("") }
    var hospital by remember { mutableStateOf("") }
    var affiliation by remember { mutableStateOf("") }

    var moreHospitals by remember { mutableStateOf(false) }
    var moreAffiliations by remember { mutableStateOf(false) }
    var additionalHospital by remember { mutableStateOf("") }
    var additionalAffiliation by remember { mutableStateOf("") }

    OutlinedTextField(
        value = about,
        onValueChange = { about = it },
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
        onValueChange = { phoneNumber = it },
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
        onValueChange = { workSchedule = it },
        minLines = 1,
        maxLines = 3,
        label = {
            Text(
                fontFamily = fontFamily,
                text = "Work Schedule"
            )
        },
        placeholder = {
            Text(
                fontFamily = fontFamily,
                color = Color.Gray,
                text = "9 to 5 every weekdays"
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
        value = hospital,
        onValueChange = { hospital = it },
        minLines = 1,
        maxLines = 1,
        label = {
            Text(
                fontFamily = fontFamily,
                text = "Hospital"
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

    Text(
        fontFamily = fontFamily,
        fontSize = 13.sp,
        text = "Do you attend more than one hospital?",
        color = FigmaBlue,
        modifier = Modifier.offset(y = 5.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.selectableGroup()
    ) {
        RadioButton(
            selected = moreHospitals,
            onClick = {
                moreHospitals = true
            },
            colors = RadioButtonDefaults.colors(
                unselectedColor = Color.Gray,
                selectedColor = FigmaBlue
            )
        )

        Text(
            fontFamily = fontFamily,
            fontSize = 13.sp,
            text = "Yes",
            color = FigmaBlue,
            modifier = Modifier.offset(x = -(8).dp)
        )

        RadioButton(
            selected = !moreHospitals,
            onClick = {
                moreHospitals = false
            },
            colors = RadioButtonDefaults.colors(
                unselectedColor = Color.Gray,
                selectedColor = FigmaBlue
            )
        )

        Text(
            fontFamily = fontFamily,
            fontSize = 13.sp,
            text = "No",
            color = FigmaBlue,
            modifier = Modifier.offset(x = -(8).dp)
        )
    }

    if (moreHospitals) {
        OutlinedTextField(
            value = additionalHospital,
            onValueChange = { additionalHospital = it },
            minLines = 1,
            maxLines = 1,
            label = {
                Text(
                    fontFamily = fontFamily,
                    text = "Additional Hospital"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Alabaster,
                unfocusedContainerColor = Alabaster,
                focusedBorderColor = FigmaBlue,
                unfocusedBorderColor = FigmaBlue,
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
    }

    OutlinedTextField(
        value = affiliation,
        onValueChange = { affiliation = it },
        minLines = 1,
        maxLines = 1,
        label = {
            Text(
                fontFamily = fontFamily,
                text = "Affiliation"
            )
        },
        placeholder = {
            Text(
                fontFamily = fontFamily,
                color = Color.Gray,
                text = "Philippine Neurological Association"
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

    Text(
        fontFamily = fontFamily,
        fontSize = 13.sp,
        text = "Are you affiliated with more associations?",
        color = FigmaBlue,
        modifier = Modifier.offset(y = 5.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.selectableGroup()
    ) {
        RadioButton(
            selected = moreAffiliations,
            onClick = {
                moreAffiliations = true
            },
            colors = RadioButtonDefaults.colors(
                unselectedColor = Color.Gray,
                selectedColor = FigmaBlue
            )
        )

        Text(
            fontFamily = fontFamily,
            fontSize = 13.sp,
            text = "Yes",
            color = FigmaBlue,
            modifier = Modifier.offset(x = -(8).dp)
        )

        RadioButton(
            selected = !moreAffiliations,
            onClick = {
                moreAffiliations = false
            },
            colors = RadioButtonDefaults.colors(
                unselectedColor = Color.Gray,
                selectedColor = FigmaBlue
            )
        )

        Text(
            fontFamily = fontFamily,
            fontSize = 13.sp,
            text = "No",
            color = FigmaBlue,
            modifier = Modifier.offset(x = -(8).dp)
        )
    }

    if (moreAffiliations) {
        OutlinedTextField(
            value = additionalAffiliation,
            onValueChange = { additionalAffiliation = it },
            minLines = 1,
            maxLines = 1,
            label = {
                Text(
                    fontFamily = fontFamily,
                    text = "Additional Affiliation"
                )
            },
            placeholder = {
                Text(
                    fontFamily = fontFamily,
                    color = Color.Gray,
                    text = "Philippine Neurological Association"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Alabaster,
                unfocusedContainerColor = Alabaster,
                focusedBorderColor = FigmaBlue,
                unfocusedBorderColor = FigmaBlue,
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
    }

    Button(
        onClick = {
            userProfileViewModel.addUserDetails(
                aboutUser = about,
                phoneNumber = phoneNumber,
                workSchedule = workSchedule,
                hospital = hospital,
                extraHospital = additionalHospital,
                affiliation = affiliation,
                extraAffiliation = additionalAffiliation
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
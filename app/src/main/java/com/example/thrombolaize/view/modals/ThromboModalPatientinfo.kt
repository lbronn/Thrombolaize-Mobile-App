@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.thrombolaize.view.modals

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.ThrombolaizeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ThromboModalPatientInfo(onDismissRequest: () -> Unit, navController: NavController) {
    val hideKeyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    val thrombolaizeViewModel: ThrombolaizeViewModel = hiltViewModel()
    val ctScanImageURL by thrombolaizeViewModel.currentCTScanURL.collectAsState()

    var patientAge by remember { mutableStateOf("") }
    var patientSex by remember { mutableStateOf("") }
    var strokeOnset by remember { mutableStateOf("") }
    var timeOfArrival by remember { mutableStateOf("") }
    var approveInformation by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val isPatientInformationComplete by remember(
        patientAge,
        patientSex,
        strokeOnset,
        timeOfArrival,
        ctScanImageURL,
        approveInformation
    ) {
        mutableStateOf(
            patientAge.isNotBlank() &&
                    patientSex.isNotBlank() &&
                    strokeOnset.isNotBlank() &&
                    timeOfArrival.isNotBlank() &&
                    ctScanImageURL != null && approveInformation
        )
    }

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (isLoading) .3f else 1f)
        ) {
            OutlinedTextField(
                singleLine = true,
                value = patientAge,
                onValueChange = { patientAge = it },
                minLines = 1,
                maxLines = 1,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "Patient's Age"
                    )
                },
                placeholder = {
                    Text(
                        fontFamily = fontFamily,
                        color = Color.Gray,
                        text = "24"
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "patient age icon"
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
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                )
            )

            OutlinedTextField(
                singleLine = true,
                value = patientSex,
                onValueChange = { patientSex = it },
                minLines = 1,
                maxLines = 1,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "Patient's Biological Sex"
                    )
                },
                placeholder = {
                    Text(
                        fontFamily = fontFamily,
                        color = Color.Gray,
                        text = "Male or Female"
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "patient sex icon"
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
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                )
            )

            OutlinedTextField(
                singleLine = true,
                value = strokeOnset,
                onValueChange = { strokeOnset = it },
                minLines = 1,
                maxLines = 1,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "Hours from Stroke Onset "
                    )
                },
                placeholder = {
                    Text(
                        fontFamily = fontFamily,
                        color = Color.Gray,
                        text = "2.5 hours"
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.time_vector),
                        contentDescription = "patient onset icon",
                        tint = FigmaBlue
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
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                )
            )

            OutlinedTextField(
                singleLine = true,
                value = timeOfArrival,
                onValueChange = { timeOfArrival = it },
                minLines = 1,
                maxLines = 1,
                label = {
                    Text(
                        fontFamily = fontFamily,
                        text = "Time of Arrival (Hospital)"
                    )
                },
                placeholder = {
                    Text(
                        fontFamily = fontFamily,
                        color = Color.Gray,
                        text = "11:59 AM"
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.time_vector),
                        contentDescription = "hospital arrival icon",
                        tint = FigmaBlue
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
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        hideKeyboard?.hide()
                    }
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.selectableGroup()
            ) {
                Checkbox(
                    checked = approveInformation,
                    onCheckedChange = {
                        approveInformation = it
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Color.Gray,
                        checkedColor = FigmaBlue
                    ),
                    modifier = Modifier.padding(start = 18.dp)
                )

                Text(
                    fontFamily = fontFamily,
                    fontSize = 13.sp,
                    text = "I agree to share the patient's information to Thrombolaize and to its" +
                            " users.",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .offset(x = -(5).dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                enabled = isPatientInformationComplete && !isLoading,
                onClick = {
                    if (!isPatientInformationComplete) return@Button
                    isLoading = true

                    thrombolaizeViewModel.savePatientDetails(
                        patientAge = patientAge,
                        patientSex = patientSex,
                        strokeOnset = strokeOnset,
                        timeOfArrival = timeOfArrival
                    ) { success, message ->
                        if (success) {
                            coroutineScope.launch {
                                delay(10_000)
                                patientAge = ""
                                patientSex = ""
                                strokeOnset = ""
                                timeOfArrival = ""
                                approveInformation = false
                                thrombolaizeViewModel.clearCTScan()
                                isLoading = false
                                bottomSheetState.hide()
                                onDismissRequest()
                                navController.navigate(Screens.ThrombolaizeResult.route)
                            }
                        } else {
                            Toast.makeText(
                                context,
                                message ?: "Failed to save profile.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(FigmaBlue),
                border = if (isPatientInformationComplete) BorderStroke(3.dp, FigmaBlue) else null,
                contentPadding = PaddingValues(
                    start = 90.dp,
                    end = 90.dp,
                    top = 15.dp,
                    bottom = 15.dp
                ),
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Thrombolaize",
                    color = if (isPatientInformationComplete) Color.White else FigmaBlue
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
        }

        if (isLoading) {
            CircularProgressIndicator(
                color = FigmaBlue,
                strokeWidth = 3.dp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 130.dp)
            )
        }
    }
}
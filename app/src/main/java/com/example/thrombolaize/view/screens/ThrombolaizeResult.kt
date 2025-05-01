package com.example.thrombolaize.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
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
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.ThrombolaizeViewModel
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel

@Composable
fun ThrombolaizeResult(
    navController: NavController,
    createdAt: Long,
    userAuthenticateViewModel: UserAuthenticationViewModel = viewModel(),
    thrombolaizeViewModel: ThrombolaizeViewModel = hiltViewModel(),
) {
    UseLaunchEffect(userAuthenticateViewModel)

    val user = userAuthenticateViewModel.currentUser
    val currentUserUID = user?.uid ?: ""
    val allPatientDetails by thrombolaizeViewModel.currentPatientDetails.collectAsState()
    val patientsRecord = allPatientDetails.firstOrNull { it.createdAt == createdAt && it.uid == currentUserUID }
    val fallBackRecord = patientsRecord ?: allPatientDetails.maxByOrNull { it.createdAt }

    val patientCTScanURL = fallBackRecord?.patientCTScanURL
    val patientAge = fallBackRecord?.patientAge
    val patientSex = fallBackRecord?.patientSex
    val strokeOnset = fallBackRecord?.strokeOnset
    val timeOfArrival = fallBackRecord?.timeOfArrival

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Alabaster)
            .padding(top = 30.dp, bottom = 20.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Row {
            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                text = "Thrombolaize ",
                color = FigmaBlue
            )

            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                text = "Summary",
                color = Color.Black
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(300.dp)
                .offset(y = 50.dp)
        ) {
            if (!patientCTScanURL.isNullOrEmpty()) {
                AsyncImage(
                    model = patientCTScanURL,
                    contentDescription = "Patient CT Scan",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.background(Color.Transparent)
                )

                Row {
                    Text(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        text = "Patient's CT Scan",
                        color = Color.Black,
                        modifier = Modifier.offset(y = 170.dp)
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.thrombo_button_vector),
                    tint = FigmaBlue,
                    contentDescription = "Patient CT Scan",
                    modifier = Modifier
                        .background(White)
                        .size(300.dp)
                )
            }

            Row {
                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    text = "Findings from ",
                    color = Color.Black,
                    modifier = Modifier.offset(y = 205.dp)
                )

                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    text = "Thrombolaize",
                    color = FigmaBlue,
                    modifier = Modifier.offset(y = 205.dp)
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 430.dp)
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardColors(
                containerColor = White,
                contentColor = White,
                disabledContainerColor = White,
                disabledContentColor = White
            )
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    text = "Stroke:",
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )

                VerticalDivider(
                    color = FigmaBlue,
                    thickness = 1.dp,
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    text = "Stroke Onset: ",
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp)
                )

                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    text = "$strokeOnset",
                    color = FigmaBlue,
                )
            }

            HorizontalDivider(
                color = FigmaBlue,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 20.dp)
            )

            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    text = "Patient is $patientAge years old and a $patientSex. The patient arrived at the " +
                            "hospital at around $timeOfArrival and the stroke happened $strokeOnset ago. " +
                            "Based on the initial CT Scan after hospital arrival and stroke onset time, " +
                            "the patient is having 95% hyperacute ischemic stroke and is recommended for " +
                            "tPA therapy.",
                    textAlign = TextAlign.Justify,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp, end = 16.dp)
                )
            }

            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                fontSize = 11.sp,
                text = "Note: A doctor's final prognosis is still needed.",
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 13.dp)
            )
        }

        Button(
            onClick = {
                navController.navigate(Screens.Home.route)
            },
            colors = ButtonDefaults.buttonColors(FigmaBlue),
            border = BorderStroke(3.dp, FigmaBlue),
            contentPadding = PaddingValues(start = 90.dp, end = 90.dp, top = 15.dp, bottom = 15.dp),
            modifier = Modifier.padding(top = 755.dp)
        ) {
            Text(
                fontFamily = fontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                text = "Back to Home",
                color = White
            )
        }
    }
}
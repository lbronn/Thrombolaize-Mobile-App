@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thrombolaize.view.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel
import kotlinx.coroutines.launch

@Composable
fun LogoutBottomModalSheet(onDismissRequest: () -> Unit, userAuthenticateViewModel: UserAuthenticationViewModel = viewModel(), navController: NavController) {
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
        dragHandle = {
            BottomSheetDefaults.DragHandle()
        },
        containerColor = Alabaster,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 15.dp)
            ) {
                Text(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    text = "Are you sure you want to log out?",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetState.hide()
                                onDismissRequest()
                            }
                            userAuthenticateViewModel.logout()
                            navController.navigate(Screens.Login.route)
                        },
                        colors = ButtonDefaults.buttonColors(Color.Red),
                        contentPadding = PaddingValues(
                            start = 40.dp,
                            end = 40.dp,
                            top = 15.dp,
                            bottom = 15.dp
                        )
                    ) {
                        Text(
                            fontFamily = fontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            text = "Yes",
                            color = White
                        )
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetState.hide()
                                onDismissRequest()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(2.dp, Color.Red),
                        contentPadding = PaddingValues(
                            start = 40.dp,
                            end = 40.dp,
                            top = 15.dp,
                            bottom = 15.dp
                        )
                    ) {
                        Text(
                            fontFamily = fontFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            text = "No",
                            color = Color.Red
                        )
                    }
                }
            }
        }
    )
}

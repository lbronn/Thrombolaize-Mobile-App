package com.example.thrombolaize.view.screens

    import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thrombolaize.R
import com.example.thrombolaize.routes.Screens
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.view.modals.ProfileInfoCard
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel

@Composable
fun Profile(userAuthenticateViewModel: UserAuthenticationViewModel = viewModel(), navController: NavController) {
    LaunchedEffect(Unit) {
        userAuthenticateViewModel.fetchLoggedInUserIfNeeded()
    }

    val context = LocalContext.current
    val user = userAuthenticateViewModel.currentUser
    val displayName = if (user != null) {
        "Dr. ${user.firstName} ${user.lastName}"
    } else {
        "No name found!"
    }
    val displaySpecialty = user?.specialty ?: "No specialty found!"
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_pic),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.FillWidth
                    )
                }

                IconButton (
                    onClick = {
                        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 10.dp, y = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.edit_vector),
                        contentDescription = "edit profile",
                        tint = White,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(FigmaBlue)
                            .padding(5.dp)
                    )
                }
            }

            Text(
                text = displayName,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 20.dp)
            )
            Text(
                text = displaySpecialty,
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                fontSize = 14.sp,
                color = FigmaBlue
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            ProfileInfoCard(
                painter = painterResource(R.drawable.calender_vector),
                title = "Earliest Available Schedule",
                value = "20 April 2025"
            )
            ProfileInfoCard(
                painter = painterResource(R.drawable.hospital_vector),
                title = "Hospital",
                value = "Chong Hua Hospital"
            )
            ProfileInfoCard(
                painter = painterResource(R.drawable.work_vector),
                title = "Affiliations",
                value = "Philippine Neurological Association",
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                navController.navigate(Screens.Login.route)
                userAuthenticateViewModel.logout()
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
            border = BorderStroke(3.dp, Color.Red),
            contentPadding = PaddingValues(start = 50.dp, end = 50.dp, top = 15.dp, bottom = 15.dp),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 20.dp)
        ) {
            Text(
                fontFamily = fontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                text = "Logout",
                color = White
            )
        }
    }
}
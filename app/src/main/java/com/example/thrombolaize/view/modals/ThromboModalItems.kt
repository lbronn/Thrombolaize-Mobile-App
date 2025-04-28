package com.example.thrombolaize.view.modals

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.thrombolaize.R
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.UserProfileViewModel

@Composable
fun ThromboModalItems() {
    val scrollState = rememberScrollState()

    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = GetContent()) { uri: Uri? ->
        imageUri = uri
        uri?.let { userProfileViewModel.uploadProfile(it) }
    }
    val imageUrl by userProfileViewModel.currentPictureURL.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 15.dp)
        ) {
            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 23.sp,
                text = "Upload ",
                color = Color.Black,
                modifier = Modifier.padding(start = 20.dp)
            )

            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 23.sp,
                text = "Patient CT Scan ",
                color = FigmaBlue,
                modifier = Modifier.padding()
            )

            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 23.sp,
                text = "Here",
                color = Color.Black,
                modifier = Modifier.padding(end = 20.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp)
                .zIndex(1f)
                .clickable {
                    launcher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "profile picture",
                    modifier = Modifier.size(300.dp)
                )

                Row {
                    Text(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        maxLines = 1,
                        text = "Patient's ",
                        modifier = Modifier.offset(y = 170.dp)
                    )

                    Text(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = FigmaBlue,
                        maxLines = 1,
                        text = "brain CT scan",
                        modifier = Modifier.offset(y = 170.dp)
                    )
                }
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.thrombo_button_vector),
                    contentDescription = "image placeholder",
                    tint = White,
                    modifier = Modifier
                        .background(FigmaBlue)
                        .size(300.dp)
                )

                Row {
                    Text(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        maxLines = 1,
                        text = "Tap to add ",
                        modifier = Modifier.offset(y = 170.dp)
                    )

                    Text(
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        color = FigmaBlue,
                        maxLines = 1,
                        text = "patient brain CT scan",
                        modifier = Modifier.offset(y = 170.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            maxLines = 1,
            text = "Basic Patient Information",
        )

        ThromboModalPatientInfo()
    }
}
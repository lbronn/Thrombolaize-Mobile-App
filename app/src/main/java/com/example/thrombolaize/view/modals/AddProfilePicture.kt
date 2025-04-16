package com.example.thrombolaize.view.modals

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
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
fun AddProfilePicture(userProfileViewModel: UserProfileViewModel = hiltViewModel()) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = GetContent()) { uri: Uri? ->
        imageUri = uri
        uri?.let { userProfileViewModel.uploadProfile(it) }
    }
    val imageUrl by userProfileViewModel.currentPictureURL.collectAsState()

    Box(
        modifier = Modifier
            .offset(y = 12.dp)
            .height(170.dp)
            .width(170.dp)
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
                modifier = Modifier.matchParentSize()
            )

            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = FigmaBlue,
                maxLines = 1,
                text = "Tap to Replace Picture",
                modifier = Modifier.offset(y = 85.dp)
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.person_vector),
                contentDescription = "image placeholder",
                tint = White,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(FigmaBlue)
                    .padding(12.dp)
                    .size(80.dp)
            )

            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = FigmaBlue,
                maxLines = 1,
                text = "Tap to Upload Picture",
                modifier = Modifier.offset(y = 73.dp)
            )
        }
    }
}


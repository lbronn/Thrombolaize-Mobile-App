package com.example.thrombolaize.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrombolaize.R
import com.example.thrombolaize.ui.theme.Alabaster
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.TopGradient
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.ui.theme.rectangleShape

@Preview
@Composable
fun Profile() {
    Icon(
        imageVector = Icons.Filled.Settings,
        contentDescription = "Edit Profile",
        tint = Color.White,
        modifier = Modifier
            .size(20.dp)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(rectangleShape)
                .background(TopGradient)
        )

        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "edit profile",
            tint = White,
            modifier = Modifier
                .size(50.dp)
                .fillMaxWidth()
                .align(Alignment.TopEnd)
                .padding(end = 15.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = "Profile Picture"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Dr. Joshua Abejero",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black
            )
            Text(
                text = "Neurologist",
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                color = FigmaBlue
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            ProfileInfoCard(
                painter = painterResource(R.drawable.calender_vector),
                title = "Earliest Available Schedule",
                value = "1 January 2020"
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
}

@Composable
fun ProfileInfoCard(painter: Painter, title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .border(3.dp, FigmaBlue, RoundedCornerShape(10.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painter,
                contentDescription = "icons",
                tint = FigmaBlue
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {
                Text(
                    text = title,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    color = FigmaBlue
                )
                Text(
                    text = value,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

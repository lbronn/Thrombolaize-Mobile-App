package com.example.thrombolaize.view.modals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily

@Composable
fun ProfileInfoCard(painter: Painter, title: String, firstSubtitle: String? = null, secondSubtitle: String? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 13.dp, vertical = 9.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardColors(
            containerColor = White,
            contentColor = White,
            disabledContainerColor = White,
            disabledContentColor = White
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painter,
                contentDescription = "icons",
                tint = FigmaBlue,
                modifier = Modifier.size(25.dp)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {
                Text(
                    text = title,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = FigmaBlue
                )
                if (firstSubtitle != null) {
                    Text(
                        text = firstSubtitle,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
                if (secondSubtitle != null) {
                    Text(
                        text = secondSubtitle,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
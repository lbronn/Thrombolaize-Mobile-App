package com.example.thrombolaize.view.modals

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.fontFamily

@Composable
fun AboutThrombolaize() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .offset(y = 510.dp)
            .padding(horizontal = 13.dp, vertical = 9.dp),
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        )
    ) {
        Text(
            text = "Thrombolaize™",
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            color = FigmaBlue,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Rapid Thrombolysis Decision-Making",
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            color = FigmaBlue,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "A Thesis Project Made By SamBaYa Group.",
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 10.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}
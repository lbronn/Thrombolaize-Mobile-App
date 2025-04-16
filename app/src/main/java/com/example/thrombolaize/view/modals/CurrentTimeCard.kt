package com.example.thrombolaize.view.modals

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentTimeCard() {
    var currentTime by remember {
        mutableStateOf(getFormattedTime())
    }
    var currentDate by remember {
        mutableStateOf(getFormattedDate())
    }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getFormattedTime()
            delay(1_000L)

            currentDate = getFormattedDate()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 8.dp)
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
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                fontFamily = fontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                text = currentDate,
                color = FigmaBlue,
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
                fontSize = 16.sp,
                text = currentTime,
                color = FigmaBlue,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedTime(): String {
    val timeFormat = DateTimeFormatter.ofPattern("hh:mm a")
    return LocalTime.now().format(timeFormat)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedDate(): String {
    val dateFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)
    return LocalDate.now().format(dateFormat)
}
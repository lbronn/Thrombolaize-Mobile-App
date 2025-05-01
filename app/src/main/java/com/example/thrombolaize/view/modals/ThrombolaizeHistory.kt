package com.example.thrombolaize.view.modals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thrombolaize.model.PatientDetails
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.ThrombolaizeViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ThrombolaizeHistory(navController: NavController, thrombolaizeViewModel: ThrombolaizeViewModel = hiltViewModel()) {
    val thrombolaizeHistory by thrombolaizeViewModel.currentPatientDetails.collectAsState()

    if (thrombolaizeHistory.isEmpty()) {
        Text(
            fontFamily = fontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = Color.Black,
            text = "No Thrombolaize history yet.",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 155.dp)
        )
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .offset(y = 150.dp)
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
        LazyColumn {
            itemsIndexed(
                items = thrombolaizeHistory.sortedBy { it.createdAt },
                key = { _, item -> item.createdAt }
            ) { idx, item ->
                HistoryRow(item, idx + 1, navController)

                HorizontalDivider(
                    color = FigmaBlue,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun HistoryRow(
    item: PatientDetails,
    patientNumber: Int,
    navController: NavController,
) {
    val dateText = remember(item.createdAt) {
        val fmt = SimpleDateFormat("MMM dd yyyy • hh:mm a", Locale.getDefault())
        fmt.format(Date(item.createdAt))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navController.navigate("thrombo_result/${item.createdAt}")
            }
    ) {
        Text(
            text = "Patient $patientNumber: ",
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = FigmaBlue,
            modifier = Modifier.offset(y = 3.dp)
        )

        Text(
            text = dateText,
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = Color.DarkGray,
            modifier = Modifier.offset(y = 3.dp)
        )
    }
}
package com.example.thrombolaize.view.modals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
fun ProfileInfoCard(
    painter: Painter,
    title: String,
    firstSubtitle: String,
    secondSubtitle: String? = null
) {
    Card(
        modifier = Modifier
            .offset(y = (-42).dp)
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
                Text(
                    text = firstSubtitle,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    color = Color.Black
                )
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

@Composable
fun CombinedProfileInfoCard(
    firstPainter: Painter,
    firstTitle: String,
    firstSubtitle: String,
    secondPainter: Painter,
    secondTitle: String,
    secondSubtitle: String
) {
    Card(
        modifier = Modifier
            .offset(y = (-42).dp)
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = firstPainter,
                    contentDescription = "icons",
                    tint = FigmaBlue,
                    modifier = Modifier.size(25.dp)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column {
                    Text(
                        text = firstTitle,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = FigmaBlue
                    )
                    Text(
                        text = firstSubtitle,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                thickness = 1.dp,
                color = FigmaBlue
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = secondPainter,
                    contentDescription = "icons",
                    tint = FigmaBlue,
                    modifier = Modifier.size(25.dp)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column {
                    Text(
                        text = secondTitle,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = FigmaBlue
                    )
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

@Composable
fun MultipleTitleProfileInfoCard(
    firstPainter: Painter,
    firstTitle: String,
    firstSubtitle1: String,
    firstSubtitle2: String? = null,
    secondPainter: Painter,
    secondTitle: String,
    secondSubtitle1: String,
    secondSubtitle2: String? = null,
) {
    Card(
        modifier = Modifier
            .offset(y = (-42).dp)
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = firstPainter,
                    contentDescription = "icons",
                    tint = FigmaBlue,
                    modifier = Modifier.size(25.dp)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column {
                    Text(
                        text = firstTitle,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = FigmaBlue
                    )
                    Text(
                        text = firstSubtitle1,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    if (firstSubtitle2 != null) {
                        Text(
                            text = firstSubtitle2,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 15.dp),
                thickness = 1.dp,
                color = FigmaBlue
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = secondPainter,
                    contentDescription = "icons",
                    tint = FigmaBlue,
                    modifier = Modifier.size(25.dp)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column {
                    Text(
                        text = secondTitle,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = FigmaBlue
                    )
                    Text(
                        text = secondSubtitle1,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.W600,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    if (secondSubtitle2 != null) {
                        Text(
                            text = secondSubtitle2,
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
}
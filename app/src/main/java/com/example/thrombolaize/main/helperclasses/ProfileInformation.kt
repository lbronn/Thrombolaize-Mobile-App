package com.example.thrombolaize.main.helperclasses

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.thrombolaize.R
import com.example.thrombolaize.view.modals.ProfileInfoCard

@Composable
fun profileInfo(): List<Unit> {
    return listOf(
        ProfileInfoCard(
            painter = painterResource(R.drawable.contact_vector),
            title = "Mobile Number",
            firstSubtitle = "+63 920 412 5528",
        ),
        ProfileInfoCard(
            painter = painterResource(R.drawable.work_vector),
            title = "Work Schedule",
            firstSubtitle = "9:00 AM to 5:00 PM every Monday to Friday"
        ),
        ProfileInfoCard(
            painter = painterResource(R.drawable.hospital_vector),
            title = "Hospital",
            firstSubtitle = "Chong Hua Hospital",
            secondSubtitle = "Cebu Doctors' University-Hospital"
        ),
        ProfileInfoCard(
            painter = painterResource(R.drawable.affiliations_vector),
            title = "Affiliations",
            firstSubtitle = "Philippine Neurological Association",
            secondSubtitle = "Stroke Society of the Philippines"
        ),
        ProfileInfoCard(
            painter = painterResource(R.drawable.affiliations_vector),
            title = "Affiliations",
            firstSubtitle = "Philippine Neurological Association",
            secondSubtitle = "Stroke Society of the Philippines"
        ),
        ProfileInfoCard(
            painter = painterResource(R.drawable.affiliations_vector),
            title = "Affiliations",
            firstSubtitle = "Philippine Neurological Association",
            secondSubtitle = "Stroke Society of the Philippines"
        )
    )
}
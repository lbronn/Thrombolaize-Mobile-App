package com.example.thrombolaize.main.helperclasses

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.thrombolaize.R
import com.example.thrombolaize.view.modals.CombinedProfileInfoCard
import com.example.thrombolaize.view.modals.MultipleTitleProfileInfoCard
import com.example.thrombolaize.view.modals.ProfileInfoCard

@Composable
fun profileInfo(): List<Unit> {
    return listOf(
        ProfileInfoCard(
            painter = painterResource(R.drawable.person_vector),
            title = "About Me",
            firstSubtitle = "I am Dr. Bronn, and I like to help people. I am a neurologist for around 15 years. " +
                    "Doctor is my calling since I was a child."
        ),
        CombinedProfileInfoCard(
            firstPainter = painterResource(R.drawable.contact_vector),
            firstTitle = "Mobile Number",
            firstSubtitle = "+63 920 412 5528",
            secondPainter = painterResource(R.drawable.work_vector),
            secondTitle = "Work Schedule",
            secondSubtitle = "9:00 AM to 5:00 PM every Monday to Friday"
        ),
        MultipleTitleProfileInfoCard(
            firstPainter = painterResource(R.drawable.hospital_vector),
            firstTitle = "Hospital",
            firstSubtitle1 = "Chong Hua Hospital",
            firstSubtitle2 = "Cebu Doctors' University-Hospital",
            secondPainter = painterResource(R.drawable.affiliations_vector),
            secondTitle = "Affiliations",
            secondSubtitle1 = "Philippine Neurological Association",
            secondSubtitle2 = "Stroke Society of the Philippines"
        )
    )
}
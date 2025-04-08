package com.example.thrombolaize.main.helperclasses

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrombolaize.R
import com.example.thrombolaize.view.modals.CombinedProfileInfoCard
import com.example.thrombolaize.view.modals.MultipleTitleProfileInfoCard
import com.example.thrombolaize.view.modals.ProfileInfoCard
import com.example.thrombolaize.viewmodel.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun profileInfo(): List<Unit> {
    val userProfileViewModel: UserProfileViewModel = viewModel()
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val allUserDetails by userProfileViewModel.currentUserDetails.collectAsState()
    val currentUserDetails = allUserDetails.firstOrNull {
        it.uid == currentUserUid
    }

    return listOf(
        ProfileInfoCard(
            painter = painterResource(R.drawable.person_vector),
            title = "About Me",
            firstSubtitle = currentUserDetails?.aboutUser ?: "No details provided."
        ),
        CombinedProfileInfoCard(
            firstPainter = painterResource(R.drawable.contact_vector),
            firstTitle = "Mobile Number",
            firstSubtitle = currentUserDetails?.phoneNumber ?: "No details provided.",
            secondPainter = painterResource(R.drawable.work_vector),
            secondTitle = "Work Schedule",
            secondSubtitle = currentUserDetails?.workSchedule ?: "No details provided."
        ),
        MultipleTitleProfileInfoCard(
            firstPainter = painterResource(R.drawable.hospital_vector),
            firstTitle = "Hospital",
            firstSubtitle1 = currentUserDetails?.hospital ?: "No details provided.",
            firstSubtitle2 = currentUserDetails?.extraHospital ?: "No details provided.",
            secondPainter = painterResource(R.drawable.affiliations_vector),
            secondTitle = "Affiliation",
            secondSubtitle1 = currentUserDetails?.affiliation ?: "No details provided.",
            secondSubtitle2 = currentUserDetails?.extraAffiliation ?: "No details provided."
        )
    )
}
package com.example.thrombolaize.view.modals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrombolaize.model.User
import com.example.thrombolaize.ui.theme.FigmaBlue
import com.example.thrombolaize.ui.theme.White
import com.example.thrombolaize.ui.theme.fontFamily
import com.example.thrombolaize.viewmodel.UserAuthenticationViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MessageToUsersCard(userAuthenticateViewModel: UserAuthenticationViewModel = viewModel(), onUserSelected: (User) -> Unit) {
    val allUsers by userAuthenticateViewModel.currentUsers.collectAsState()
    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val filteredUsers = allUsers.filter {
        it.uid != currentUserUid
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        items(filteredUsers) { user ->
            val displayName = "${user.firstName} ${user.lastName}"
            Card(
                modifier = Modifier
                    .size(width = 85.dp, height = 60.dp)
                    .clickable {
                        onUserSelected(user)
                    },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Text(
                    text = displayName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 8.dp),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = FigmaBlue,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
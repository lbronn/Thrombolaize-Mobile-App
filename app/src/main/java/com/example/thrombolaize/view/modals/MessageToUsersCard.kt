package com.example.thrombolaize.view.modals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrombolaize.R
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

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 30.dp, vertical = 10.dp)
    ) {
        items(filteredUsers) { user ->
            val displayName = "${user.firstName} ${user.lastName}"
            Card(
                modifier = Modifier
                    .width(400.dp)
                    .height(60.dp)
                    .clickable {
                        onUserSelected(user)
                    },
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .offset(x = 52.dp)
                        .padding(18.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.person_vector),
                        contentDescription = "colleague icon",
                        tint = FigmaBlue,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = displayName,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = FigmaBlue,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
package com.chebitoch.fitfire.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chebitoch.fitfire.viewmodel.ProfileViewModel
import com.chebitoch.fitfire.viewmodel.UserProfile as ViewModelUserProfile
import com.chebitoch.fitfire.navigation.ROUT_EDIT_PROFILE





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel // Receive the ViewModel
) {
    val userProfile by profileViewModel.userProfile.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                actions = {
                    Button(
                        onClick = { navController.navigate(ROUT_EDIT_PROFILE) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text("Edit", color = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        ProfileContent(
            userProfile = userProfile,
            padding = padding
        )
    }
}

@Composable
fun ProfileContent( // Removed the redundant @Composable annotation
    userProfile: ViewModelUserProfile,
    padding: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(userProfile.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Goal: ${userProfile.goal}", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStat(title = "Age", value = "${userProfile.age}")
            ProfileStat(title = "Height", value = "${userProfile.height} cm")
            ProfileStat(title = "Weight", value = "${userProfile.weight} kg")
        }
    }
}

@Composable
fun ProfileStat(title: String, value: String) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
        }
    }
}


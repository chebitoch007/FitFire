package com.chebitoch.fitfire.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chebitoch.fitfire.viewmodel.ProfileViewModel
import com.chebitoch.fitfire.viewmodel.UserProfile as ViewModelUserProfile // Alias to avoid naming conflict
import com.chebitoch.fitfire.model.UserProfile as RoomUserProfile


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userProfile by profileViewModel.userProfile.collectAsState()
    val context = LocalContext.current // Get context for database initialization

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        ProfileContent(
            userProfile = userProfile,
            padding = padding,
            onSaveProfile = { profileViewModel.saveUserProfile(it) } // Pass the save function
        )
    }
}

@Composable
fun ProfileContent(
    userProfile: ViewModelUserProfile,
    padding: PaddingValues = PaddingValues(),
    onSaveProfile: (RoomUserProfile) -> Unit // Callback to save profile
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

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                // For demonstration, let's save the current displayed data
                val roomUserProfile = RoomUserProfile(
                    name = userProfile.name,
                    age = userProfile.age,
                    height = userProfile.height,
                    weight = userProfile.weight,
                    goal = userProfile.goal
                )
                onSaveProfile(roomUserProfile)
                // TODO: Navigate to edit profile screen
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Edit Profile")
        }

        OutlinedButton(
            onClick = {
                // TODO: Add logout logic if applicable
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout", color = MaterialTheme.colorScheme.error)
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
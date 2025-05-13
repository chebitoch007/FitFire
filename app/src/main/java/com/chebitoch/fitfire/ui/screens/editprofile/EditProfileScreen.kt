package com.chebitoch.fitfire.ui.screens.editprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chebitoch.fitfire.viewmodel.ProfileViewModel
import com.chebitoch.fitfire.viewmodel.UserProfile


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, profileViewModel: ProfileViewModel) {
    val userProfile by profileViewModel.userProfile.collectAsState()
    var name by remember { mutableStateOf(userProfile.name) }
    var age by remember { mutableStateOf(userProfile.age.toString()) }
    var height by remember { mutableStateOf(userProfile.height.toString()) }
    var weight by remember { mutableStateOf(userProfile.weight.toString()) }
    var goal by remember { mutableStateOf(userProfile.goal) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            val updatedProfile = UserProfile( // Use the ViewModel's UserProfile
                                id = userProfile.id,
                                name = name,
                                age = age.toIntOrNull() ?: 0,
                                height = height.toIntOrNull() ?: 0,
                                weight = (weight.toFloatOrNull() ?: 0f).toDouble(),
                                goal = goal
                            )
                            profileViewModel.saveUserProfile(updatedProfile)
                            navController.popBackStack() // Go back to the profile screen after saving
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        Text("Save", color = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = age,
                onValueChange = { age = it.filter { it.isDigit() } },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = height,
                onValueChange = { height = it.filter { it.isDigit() } },
                label = { Text("Height (cm)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it.filter { it.isDigit() || it == '.' } },
                label = { Text("Weight (kg)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = goal,
                onValueChange = { goal = it },
                label = { Text("Goal") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
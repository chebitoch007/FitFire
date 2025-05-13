package com.chebitoch.fitfire.ui.screens.workoutplans

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chebitoch.fitfire.model.WorkoutPlan
import com.chebitoch.fitfire.viewmodel.WorkoutPlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutPlanScreen(
    navController: NavController,
    viewModel: WorkoutPlanViewModel = viewModel(factory = WorkoutPlanViewModel.WorkoutPlanViewModelFactory(LocalContext.current.applicationContext as Application))
) {
    var name by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Workout Plan") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(androidx.compose.material.icons.Icons.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    val newWorkoutPlan = WorkoutPlan(
                        name = name,
                        duration = duration,
                        level = level,
                        target = target,
                        // You'll likely want to let the user select an image as well
                        // For now, we'll use a default or handle it differently
                        imageResId = com.chebitoch.fitfire.R.drawable.full_body_burn // Default image
                    )
                    viewModel.insertWorkoutPlan(newWorkoutPlan)
                    navController.popBackStack() // Go back to the workout plans screen
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Save Workout Plan")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Workout Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration (e.g., 30 mins)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = level,
                onValueChange = { level = it },
                label = { Text("Level (e.g., Beginner, Intermediate, Advanced)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = target,
                onValueChange = { target = it },
                label = { Text("Target (e.g., Full Body, Legs, Core)") },
                modifier = Modifier.fillMaxWidth()
            )
            // You might want to add a way for the user to select an image here
        }
    }
}
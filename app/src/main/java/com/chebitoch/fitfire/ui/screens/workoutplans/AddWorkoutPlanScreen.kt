package com.chebitoch.fitfire.ui.screens.workoutplans

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chebitoch.fitfire.model.WorkoutPlan
import com.chebitoch.fitfire.viewmodel.WorkoutPlanViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

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
    var expanded by remember { mutableStateOf(false) }
    val levels = listOf("Beginner", "Intermediate", "Advanced")
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            selectedImageUri = uri
        }
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Workout Plan") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
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
                    if (name.isNotBlank() && duration.isNotBlank() && target.isNotBlank() && level.isNotBlank()) { // Added level check
                        // In a real app, you would upload the image and get a URL or store the Uri
                        val newWorkoutPlan = WorkoutPlan(
                            name = name,
                            duration = duration,
                            level = level,
                            target = target,
                            imageResId = com.chebitoch.fitfire.R.drawable.full_body_burn //  Use a default.
                        )
                        viewModel.insertWorkoutPlan(newWorkoutPlan)
                        navController.popBackStack()
                    } else {
                        // Optionally show an error message if required fields are empty
                    }
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
            // Image selection UI
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (selectedImageUri == null) {
                    // Show a placeholder or button to select an image
                    Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text("Select Image")
                    }
                } else {
                    // Display the selected image
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(selectedImageUri)
                                .build()
                        ),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(200.dp) // Adjust size as needed
                            .clip(MaterialTheme.shapes.medium) // Clip to a shape
                            .clickable { imagePickerLauncher.launch("image/*") }, //relaunch
                        contentScale = ContentScale.Crop, // Or any other scale
                    )
                }
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Workout Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = name.isBlank(),
                supportingText = { if (name.isBlank()) Text("Workout name is required") }
            )
            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration (e.g., 30 mins)") },
                modifier = Modifier.fillMaxWidth(),
                isError = duration.isBlank(),
                supportingText = { if (duration.isBlank()) Text("Duration is required") }
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = level,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Level") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = level.isBlank(), // Added isError here
                    supportingText = { if (level.isBlank()) Text("Level is required") } //Added supporting text
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    levels.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                level = item
                                expanded = false
                            }
                        )
                    }
                }
            }
            OutlinedTextField(
                value = target,
                onValueChange = { target = it },
                label = { Text("Target (e.g., Full Body, Legs, Core)") },
                modifier = Modifier.fillMaxWidth(),
                isError = target.isBlank(),
                supportingText = { if (target.isBlank()) Text("Target is required") }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddWorkoutPlanScreenPreview() {
    //  needed to pass a navController.  For  preview, we can use rememberNavController().
    val navController = rememberNavController()
    AddWorkoutPlanScreen(navController = navController)
}

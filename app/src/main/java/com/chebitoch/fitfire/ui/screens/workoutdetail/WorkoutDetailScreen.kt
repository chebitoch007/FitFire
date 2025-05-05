package com.chebitoch.fitfire.ui.screens.workoutdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chebitoch.fitfire.viewmodel.WorkoutPlanViewModel
import com.chebitoch.fitfire.ui.theme.FitFireTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    navController: NavController,
    workoutId: Int,
    viewModel: WorkoutPlanViewModel = viewModel()
) {
    val workoutPlans by viewModel.allPlans.collectAsState(initial = emptyList())
    val workout = workoutPlans.find { it.id == workoutId }

    if (workout == null) {
        Text("Workout not found", modifier = Modifier.padding(16.dp))
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(workout.name) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Button(
                onClick = { /* TODO: Start workout logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Start Workout")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = workout.imageResId),
                contentDescription = workout.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Text(
                workout.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "${workout.duration} • ${workout.level} • ${workout.target}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Divider()

            Text(
                text = "Workout Description",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "This is a great ${workout.target.lowercase()} workout for ${workout.level.lowercase()} level athletes. It lasts ${workout.duration.lowercase()} and is designed to help improve your endurance, strength, and flexibility.",
                fontSize = 16.sp,
                lineHeight = 22.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutDetailScreenPreview() {
    FitFireTheme {
        WorkoutDetailScreen(navController = rememberNavController(), workoutId = 1)
    }
}

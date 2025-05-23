package com.chebitoch.fitfire.ui.screens.workoutplans

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chebitoch.fitfire.model.WorkoutPlan
import com.chebitoch.fitfire.viewmodel.WorkoutPlanViewModel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Alignment
import com.chebitoch.fitfire.navigation.ROUT_ADD_WORKOUT_PLAN



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlansScreen(
    navController: NavController,
    viewModel: WorkoutPlanViewModel = viewModel(factory = WorkoutPlanViewModel.WorkoutPlanViewModelFactory(
        LocalContext.current.applicationContext as Application
    ))
) {
    val workoutPlans by viewModel.allPlans.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Plans") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(ROUT_ADD_WORKOUT_PLAN) }) { // Navigate here
                        Icon(Icons.Default.Add, contentDescription = "Add Plan", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White // Changed to white for better visibility
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(workoutPlans) { plan ->
                WorkoutPlanCard(
                    plan = plan,
                    onPlanClick = { navController.navigate("workout_detail/${plan.id}") },
                    onDeleteClick = { viewModel.deleteWorkoutPlan(plan) }
                )
            }
        }
    }
}

@Composable
fun WorkoutPlanCard(plan: WorkoutPlan, onPlanClick: () -> Unit, onDeleteClick: (WorkoutPlan) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlanClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = plan.imageResId),
                    contentDescription = "${plan.name} image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(end = 16.dp)
                )
                Column(verticalArrangement = Arrangement.Center) {
                    Text(plan.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(plan.duration, fontSize = 14.sp, color = Color.Gray)
                    Text(plan.level, fontSize = 14.sp, color = Color.Gray)
                    Text(plan.target, fontSize = 14.sp, color = Color.Gray)
                }
            }
            IconButton(onClick = { onDeleteClick(plan) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Plan", tint = Color.Red)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutPlansScreenPreview() {
    WorkoutPlansScreen(navController = rememberNavController())
}

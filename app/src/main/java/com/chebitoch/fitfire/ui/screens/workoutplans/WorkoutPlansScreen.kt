package com.chebitoch.fitfire.ui.screens.workoutplans

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.chebitoch.fitfire.data.model.WorkoutPlan
import com.chebitoch.fitfire.ui.theme.FitFireTheme
import com.chebitoch.fitfire.viewmodel.WorkoutPlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlansScreen(
    navController: NavController,
    viewModel: WorkoutPlanViewModel = viewModel()
) {
    val workoutPlans by viewModel.allPlans.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Plans") },
                actions = {
                    IconButton(onClick = { viewModel.insertSamplePlans() }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Plan", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
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
                WorkoutPlanCard(plan = plan) {
                    navController.navigate("workout_detail/${plan.id}")
                }
            }
        }
    }
}

@Composable
fun WorkoutPlanCard(plan: WorkoutPlan, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
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
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutPlansScreenPreview() {
    FitFireTheme {
        WorkoutPlansScreen(navController = rememberNavController())
    }
}

package com.chebitoch.fitfire.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chebitoch.fitfire.ui.screens.about.AboutScreen
import com.chebitoch.fitfire.ui.screens.auth.LoginScreen
import com.chebitoch.fitfire.ui.screens.auth.RegisterScreen
import com.chebitoch.fitfire.ui.screens.profile.ProfileScreen
import com.chebitoch.fitfire.ui.screens.progresstracker.ProgressTrackerScreen
import com.chebitoch.fitfire.ui.screens.splash.SplashScreen
import com.chebitoch.fitfire.ui.screens.workoutdetail.WorkoutDetailScreen
import com.chebitoch.fitfire.ui.screens.workoutplans.WorkoutPlansScreen
import com.chebitoch.fitfire.viewmodel.AuthViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chebitoch.fitfire.R


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH
) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory(context.applicationContext as Application))

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_SPLASH) {
            SplashScreen(navController)
        }

        composable(ROUT_HOME) {
            HomeScreen(navController)
        }

        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }

        composable(ROUT_PROFILE) {
            ProfileScreen(navController)
        }

        composable(ROUT_PROGRESSTRACKER) {
            ProgressTrackerScreen(navController)
        }

        composable(ROUT_WORKOUTPLANS) {
            WorkoutPlansScreen(navController)
        }

        composable("workout_detail/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.IntType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getInt("workoutId") ?: -1
            WorkoutDetailScreen(navController, workoutId)
        }

        composable(ROUT_REGISTER) {
            RegisterScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(ROUT_LOGIN) {
            LoginScreen(
                viewModel = authViewModel,
                navController = navController,
                onLoginSuccess = {
                    navController.navigate(ROUT_HOME) {
                        popUpTo(ROUT_LOGIN) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Ensures spacing between elements
    ) {
        // App Title
        Text(
            text = "FitFire",
            fontSize = 36.sp,
            color = Color(0xFF6200EE), // Deep purple color for consistency
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Image
        Image(
            painter = painterResource(R.drawable.img_4),
            contentDescription = "home",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(MaterialTheme.shapes.medium) // Rounded corners
                .shadow(4.dp) // Adds a shadow for depth
        )

        // Tagline
        Text(
            text = "GET FIT!",
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Description
        Text(
            text = "FitFire is your companion in your journey towards fitness. Improve your health and get in shape.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Card with Grid layout for buttons
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
                .shadow(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2-column grid
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    FeatureButton(
                        text = "Workout Plans",
                        onClick = { navController.navigate(ROUT_WORKOUTPLANS) }
                    )
                }

                item {
                    FeatureButton(
                        text = "Workout Detail",
                        onClick = { navController.navigate("workout_detail/1") } // Example ID
                    )
                }

                item {
                    FeatureButton(
                        text = "Check your profile",
                        onClick = { navController.navigate(ROUT_PROFILE) }
                    )
                }

                item {
                    FeatureButton(
                        text = "Track your progress",
                        onClick = { navController.navigate(ROUT_PROGRESSTRACKER) }
                    )
                }

                item {
                    FeatureButton(
                        text = "About FitFire",
                        onClick = { navController.navigate(ROUT_ABOUT) }
                    )
                }
            }
        }
    }
}

@Composable
fun FeatureButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE) // background color
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
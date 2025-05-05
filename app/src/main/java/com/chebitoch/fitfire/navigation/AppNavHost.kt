package com.chebitoch.fitfire.navigation

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

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH
) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory(context.applicationContext))

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
    // TODO: Implement Home screen UI
}
package com.chebitoch.fitfire.ui.screens.splash

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chebitoch.fitfire.R
import com.chebitoch.fitfire.navigation.ROUT_HOME
import com.chebitoch.fitfire.navigation.ROUT_LOGIN
import kotlinx.coroutines.delay

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    // Launch navigation after delay and login check
    LaunchedEffect(Unit) {
        delay(2000)

        val isLoggedIn = isUserLoggedIn(context)

        navController.navigate(if (isLoggedIn) ROUT_HOME else ROUT_LOGIN) {
            popUpTo("splash") { inclusive = true } // clear splash from back stack
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(240.dp)
                .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "FitFire",
            fontSize = 40.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF0288D1),
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Fuel your fitness journey",
            fontSize = 18.sp,
            color = Color.DarkGray
        )
    }
}

// Function to check login status using SharedPreferences
fun isUserLoggedIn(context: Context): Boolean {
    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return prefs.getBoolean("is_logged_in", false)
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(rememberNavController())
}
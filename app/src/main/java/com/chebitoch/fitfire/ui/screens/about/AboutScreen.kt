package com.chebitoch.fitfire.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chebitoch.fitfire.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About FitFire") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = "FitFire",
                fontSize = 36.sp,
                color = Color(0xFF6200EE),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // App Image
            Image(
                painter = painterResource(R.drawable.img_4),
                contentDescription = "App Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shadow(4.dp)
            )

            // Tagline
            Text(
                text = "GET FIT!",
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Short Description
            Text(
                text = "Your personal fitness companion!",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App Description Card
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "About This App",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "FitFire helps you track your fitness progress, set workout goals, and stay motivated on your journey to better health. Designed with simplicity and performance in mind, FitFire is your go-to fitness tracker app.",
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Version & Developer Info
            Text(text = "Version 1.0.0", fontSize = 12.sp, color = Color.Gray)
            Text(text = "Developed by Chebitoch Devs", fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Info
            Text(
                text = "Contact: support@fitfire.com",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF6200EE)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Legal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Privacy Policy",
                    fontSize = 14.sp,
                    color = Color(0xFF6200EE),
                    modifier = Modifier.clickable { /* Navigate to Privacy */ }
                )
                Text(
                    text = "Terms of Service",
                    fontSize = 14.sp,
                    color = Color(0xFF6200EE),
                    modifier = Modifier.clickable { /* Navigate to Terms */ }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Feedback Button
            Button(
                onClick = { /* Open app store or feedback page */ },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Rate Us ⭐")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(rememberNavController())
}
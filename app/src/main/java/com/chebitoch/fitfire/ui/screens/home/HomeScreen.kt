package com.chebitoch.fitfire.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import com.chebitoch.fitfire.navigation.ROUT_ABOUT
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.chebitoch.fitfire.navigation.ROUT_PROFILE
import com.chebitoch.fitfire.navigation.ROUT_PROGRESSTRACKER
import com.chebitoch.fitfire.navigation.ROUT_WORKOUTDETAIL
import com.chebitoch.fitfire.navigation.ROUT_WORKOUTPLANS

@Composable
fun HomeScreen(navController: NavController) {
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
            painter = painterResource(R.drawable.img),
            contentDescription = "home",
            modifier = Modifier
                .width(250.dp)
                .height(200.dp)
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
        ) { LazyVerticalGrid(
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
                    onClick = { navController.navigate(ROUT_WORKOUTDETAIL) }
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
                    text = "Track my progress",
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
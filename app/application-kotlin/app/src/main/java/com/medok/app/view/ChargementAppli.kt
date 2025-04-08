package com.medok.app.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medok.app.R
import kotlinx.coroutines.delay

@Composable
fun PageChargementApplication() {
    val dureeAnimation = 1500
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            isVisible = !isVisible
            delay(dureeAnimation.toLong()) // Change the delay to adjust the blinking speed
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = dureeAnimation) )// Adjust the duration of the animation )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the image from res/drawable/medoc.png
        Image(
            painter = painterResource(id = R.drawable.medok),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Medok",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 2.dp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Lancement de l'application",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Veuillez patienter...",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .alpha(alpha)
        )
    }
}

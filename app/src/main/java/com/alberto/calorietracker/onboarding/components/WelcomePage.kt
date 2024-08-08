package com.alberto.calorietracker.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.alberto.calorietracker.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun WelcomePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "Imagen de bienvenida",
            modifier = Modifier
                .size(350.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "¡Comienza tu transformación hoy!",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}
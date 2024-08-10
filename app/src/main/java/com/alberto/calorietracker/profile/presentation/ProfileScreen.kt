package com.alberto.calorietracker.profile.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen (logout: () -> Unit){
    Text(text = "pantalla profile",
        color = Color.White
    )
}
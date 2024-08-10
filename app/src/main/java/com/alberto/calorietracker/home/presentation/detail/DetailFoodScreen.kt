package com.alberto.calorietracker.home.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailScreen (onBack: () -> Unit){
    Column {
        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}
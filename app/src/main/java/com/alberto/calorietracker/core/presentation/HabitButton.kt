package com.alberto.calorietracker.core.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HabitButton(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9800), // Naranja
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFFF9800).copy(alpha = 0.5f),
            disabledContentColor = Color.White.copy(alpha = 0.5f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}
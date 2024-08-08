package com.alberto.calorietracker.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingEvent
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingState
import com.alberto.calorietracker.ui.theme.OrangeLight
import com.alberto.calorietracker.ui.theme.OrangeMedium
import com.alberto.calorietracker.ui.theme.Primary

@Composable
fun DataSummary(state: FitnessOnboardingState,
                onEvent: (FitnessOnboardingEvent) -> Unit, onFinish: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Primary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Resumen de tus datos",
                    style = MaterialTheme.typography.titleLarge,
                    color = OrangeLight,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                DataRow("Peso actual", "${state.weight} kg")
                DataRow("Peso objetivo", "${state.targetWeight} kg")
                DataRow("Velocidad del objetivo", state.goalSpeed)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Primary)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Calorías diarias recomendadas",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${state.caloriasDiariasRecomendadas.toInt()} kcal",
                        style = MaterialTheme.typography.headlineMedium,
                        color = OrangeMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onEvent(FitnessOnboardingEvent.SaveUserData)
                onEvent(FitnessOnboardingEvent.CompleteOnboarding)
                onFinish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrangeMedium)
        ) {
            Text("Comenzar tu viaje", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun DataRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            color = OrangeLight,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}
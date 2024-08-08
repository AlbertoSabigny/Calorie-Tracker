package com.alberto.calorietracker.onboarding.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingEvent
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingState
import com.alberto.calorietracker.ui.theme.OrangeMedium
import com.alberto.calorietracker.ui.theme.Primary

@Composable
fun GoalSpeedInput(state: FitnessOnboardingState,
                   onEvent: (FitnessOnboardingEvent) -> Unit) {
    Column(modifier = Modifier
        .padding(16.dp)
        .padding(top = 25.dp)) {

        when {
            state.weight > state.targetWeight ->  {
                SpeedOption(
                    speed = "Lento",
                    description = "Ideal para quienes buscan mantener masa muscular durante el proceso.",
                    isSelected = state.goalSpeed == "Lento",
                    onSelect = { onEvent(FitnessOnboardingEvent.UpdateGoalSpeed("Lento")) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                SpeedOption(
                    speed = "Medio",
                    description = "Equilibrio óptimo entre pérdida de grasa y conservación de músculo.",
                    isSelected = state.goalSpeed == "Medio",
                    onSelect = { onEvent(FitnessOnboardingEvent.UpdateGoalSpeed("Medio") )}
                )

                Spacer(modifier = Modifier.height(8.dp))

                SpeedOption(
                    speed = "Rápido",
                    description = "Para una pérdida de grasa más rápida, con posible pérdida de masa muscular.",
                    isSelected = state.goalSpeed == "Rápido",
                    onSelect = { onEvent(FitnessOnboardingEvent.UpdateGoalSpeed("Rápido")) }
                )
            }
            state.weight < state.targetWeight -> {
                SpeedOption(
                    speed = "Lento",
                    description = "Ideal para quienes buscan ganar peso de forma gradual y saludable.",
                    isSelected = state.goalSpeed == "Lento",
                    onSelect = { onEvent(FitnessOnboardingEvent.UpdateGoalSpeed("Lento")) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                SpeedOption(
                    speed = "Medio",
                    description = "Equilibrio óptimo entre ganancia de peso y desarrollo muscular.",
                    isSelected = state.goalSpeed == "Medio",
                    onSelect = { onEvent(FitnessOnboardingEvent.UpdateGoalSpeed("Medio")) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                SpeedOption(
                    speed = "Rápido",
                    description = "Para una ganancia de peso más rápida, con posible aumento de grasa corporal.",
                    isSelected = state.goalSpeed == "Rápido",
                    onSelect = { onEvent(FitnessOnboardingEvent.UpdateGoalSpeed("Rápido")) }
                )
            }
            else -> {
                SpeedOption(
                    speed = "Mantener",
                    description = "Ideal para los que quieran mantener peso.",
                    isSelected = state.goalSpeed == "Mantener",
                    onSelect = { onEvent(FitnessOnboardingEvent.UpdateGoalSpeed("Mantener")) }
                )
            }

        }

    }
}

@Composable
fun SpeedOption(
    speed: String,
    description: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) OrangeMedium else Primary)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelect,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.White,
                    unselectedColor = Color.White
                )
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = speed,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = description,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

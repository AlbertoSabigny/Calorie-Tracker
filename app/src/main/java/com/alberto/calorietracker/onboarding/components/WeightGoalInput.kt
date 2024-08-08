package com.alberto.calorietracker.onboarding.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingEvent
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingState

@Composable
fun WeightGoalInput(state: FitnessOnboardingState,
                    onEvent: (FitnessOnboardingEvent) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = state.targetWeight,
            onValueChange = { onEvent(FitnessOnboardingEvent.UpdateTargetWeight(it) )},
            label = { Text("Peso objetivo (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        val imc = state.imc
        val pesoIdealMinimo = state.pesoIdealMinimo
        val pesoIdealMaximo = state.pesoIdealMaximo

        Text(
            text = "Tu peso ideal según tu IMC (${String.format("%.1f", imc)}) debería estar entre ${String.format("%.1f", pesoIdealMinimo)} kg y ${String.format("%.1f", pesoIdealMaximo)} kg.",
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

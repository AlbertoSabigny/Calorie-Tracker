package com.alberto.calorietracker.onboarding.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingEvent
import com.alberto.calorietracker.onboarding.presentation.FitnessOnboardingState
import com.alberto.calorietracker.ui.theme.OrangeMedium

@Composable
fun PersonalDataInput(
    state: FitnessOnboardingState,
    onEvent: (FitnessOnboardingEvent) -> Unit) {

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = state.age,
            onValueChange = { onEvent(FitnessOnboardingEvent.UpdateAge(it)) },
            label = { Text("Edad") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.height,
            onValueChange = {
                onEvent(FitnessOnboardingEvent.UpdateHeight(it))
                onEvent(FitnessOnboardingEvent.CalculateIMCAndIdealWeight) // Calcula automáticamente al actualizar la altura
            },
            label = { Text("Estatura (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.weight,
            onValueChange = {
                onEvent(FitnessOnboardingEvent.UpdateWeight(it))
                onEvent(FitnessOnboardingEvent.CalculateIMCAndIdealWeight) // Calcula automáticamente al actualizar el peso
            },
            label = { Text("Peso actual (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Sexo", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
        Row {
            RadioButton(
                selected = state.gender == "Hombre",
                onClick = { onEvent(FitnessOnboardingEvent.UpdateGender("Hombre"))  },
                colors = RadioButtonDefaults.colors(
                    selectedColor = OrangeMedium
                )
            )
            Text(
                "Hombre",
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.White
            )
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = state.gender == "Mujer",
                onClick = { onEvent(FitnessOnboardingEvent.UpdateGender("Mujer")) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = OrangeMedium
                )
            )
            Text(
                "Mujer",
                modifier = Modifier.align(Alignment.CenterVertically),
                color = Color.White
            )
        }

        var showExplanation by remember { mutableStateOf(false) }

        Text(
            "¿Por qué preguntamos esto?",
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable { showExplanation = !showExplanation }
        )

        if (showExplanation) {
            Text(
                "Preguntamos el sexo porque es necesario para calcular con precisión tus requerimientos calóricos. Los hombres y las mujeres tienen diferentes composiciones corporales y tasas metabólicas, lo que afecta sus necesidades calóricas diarias.",
                color = Color.LightGray,
                fontSize = 8.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


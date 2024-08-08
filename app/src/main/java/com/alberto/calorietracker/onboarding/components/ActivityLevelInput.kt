package com.alberto.calorietracker.onboarding.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
fun ActivityLevelInput(state: FitnessOnboardingState,
                       onEvent: (FitnessOnboardingEvent) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 25.dp)
    ) {
        itemsIndexed(state.activityLevels) { index, activityLevel ->
            ActivityOption(
                level = activityLevel.level,
                description = activityLevel.description,
                isSelected = state.activityLevel == activityLevel.level,
                onSelect = { onEvent(FitnessOnboardingEvent.UpdateActivityLevel(activityLevel.level))  }
            )

            if (index < state.activityLevels.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ActivityOption(
    level: String,
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
                    text = level,
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

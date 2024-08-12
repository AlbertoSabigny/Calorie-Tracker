package com.alberto.calorietracker.home.presentation.diary

import androidx.compose.material3.DatePickerState
import java.time.LocalDate

data class DiaryUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val topBarText: String = "Hoy"
)
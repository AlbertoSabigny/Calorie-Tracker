package com.alberto.calorietracker.home.presentation.diary


import com.alberto.calorietracker.home.domain.model.FoodConsumed
import java.time.LocalDate

data class DiaryUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val topBarText: String = "Hoy",
    val breakfastFood: List<FoodConsumed> = emptyList(),
    val lunchFood: List<FoodConsumed> = emptyList(),
    val dinnerFood: List<FoodConsumed> = emptyList(),
    val carbs: Int = 0,
    val proteins: Int = 0,
    val fats: Int = 0,
    val calories: Int = 0
)
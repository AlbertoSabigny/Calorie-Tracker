package com.alberto.calorietracker.home.domain.model

import com.alberto.calorietracker.home.presentation.detail.MealType
import java.time.LocalDate

class FoodConsumed (
    val id: Int,
    val nombre: String,
    val date: LocalDate,
    val mealType: MealType,
    val amount: Int,
    val calories: Double,
    val proteins: Double,
    val carbohydrates: Double,
    val fats: Double
)
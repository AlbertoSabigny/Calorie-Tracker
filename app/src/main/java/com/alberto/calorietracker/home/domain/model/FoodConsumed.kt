package com.alberto.calorietracker.home.domain.model

import com.alberto.calorietracker.home.data.local.entity.MeasurementType
import com.alberto.calorietracker.home.presentation.detail.MealType
import java.time.LocalDate

data class FoodConsumed(
    val id: Int,
    val firebaseId: String,
    val nombre: String,
    val date: LocalDate,
    val mealType: MealType,
    val amount: Double,  // Cambiado de Int a Double para coincidir con la entidad
    val calories: Double,
    val proteins: Double,
    val carbohydrates: Double,
    val fats: Double,
    val measurementType: MeasurementType  // Nuevo campo añadido
)
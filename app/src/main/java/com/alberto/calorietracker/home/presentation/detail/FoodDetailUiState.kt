package com.alberto.calorietracker.home.presentation.detail

//import android.health.connect.datatypes.MealType
//import android.health.connect.datatypes.MealType
import com.alberto.calorietracker.home.data.local.entity.MeasurementType
import com.alberto.calorietracker.home.domain.model.Food

data class FoodDetailUiState(
    val food: Food? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val customAmount: String = "100",
    val selectedMealType: MealType = MealType.BREAKFAST,
    val selectedMeasurementType: MeasurementType = MeasurementType.GRAM,
    val unidadBase: String = "",
    val unidadPorcion: String = "",
    val saveSuccess: Boolean = false,
    val calories: Double = 0.0,
    val proteins: Double = 0.0,
    val carbohydrates: Double = 0.0,
    val fats: Double = 0.0
)
enum class MealType {
    BREAKFAST, LUNCH, DINNER
}



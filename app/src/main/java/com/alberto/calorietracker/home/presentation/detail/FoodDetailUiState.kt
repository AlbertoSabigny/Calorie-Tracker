package com.alberto.calorietracker.home.presentation.detail

//import android.health.connect.datatypes.MealType
//import android.health.connect.datatypes.MealType
import com.alberto.calorietracker.home.domain.model.Food

data class FoodDetailUiState(
    val food: Food? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val customAmount: String = "100",
    val selectedMealType: MealType = MealType.BREAKFAST,
    val saveSuccess: Boolean = false
)
enum class MealType {
    BREAKFAST, LUNCH, DINNER
}



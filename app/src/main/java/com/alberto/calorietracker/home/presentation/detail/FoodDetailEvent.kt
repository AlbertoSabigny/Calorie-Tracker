package com.alberto.calorietracker.home.presentation.detail

sealed class FoodDetailEvent {
    data class LoadFood(val id: String) : FoodDetailEvent()
    data class UpdateCustomAmount(val amount: String) : FoodDetailEvent()
    data class UpdateMealType(val mealType: MealType) : FoodDetailEvent()
    object SaveFoodConsumption : FoodDetailEvent()
}
package com.alberto.calorietracker.home.presentation.detail

import com.alberto.calorietracker.home.data.local.entity.MeasurementType

sealed class FoodDetailEvent {
    data class LoadFood(val id: String) : FoodDetailEvent()
    data class UpdateCustomAmount(val amount: String) : FoodDetailEvent()
    data class SaveFoodConsumption(val date: Long) : FoodDetailEvent()
    data class UpdateMealType(val mealType: MealType) : FoodDetailEvent()
    data class UpdateMeasurementType(val type: MeasurementType) : FoodDetailEvent()
}
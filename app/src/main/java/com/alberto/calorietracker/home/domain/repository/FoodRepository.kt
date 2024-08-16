package com.alberto.calorietracker.home.domain.repository

import com.alberto.calorietracker.home.data.local.entity.FoodEntity
import com.alberto.calorietracker.home.domain.model.Food
import com.alberto.calorietracker.home.presentation.detail.MealType


interface FoodRepository {
    suspend fun searchFood(query: String): List<Food>
    suspend fun getFoodById(id: String): Result<Food>

    suspend fun saveFoodConsumption(foodEntity: FoodEntity)
    suspend fun getFoodConsumptionByDate(date: Long, mealType: MealType): List<FoodEntity>
    suspend fun deleteFoodConsumption(id: Int)
}
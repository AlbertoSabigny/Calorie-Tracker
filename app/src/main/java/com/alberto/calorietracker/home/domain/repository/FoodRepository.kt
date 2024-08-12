package com.alberto.calorietracker.home.domain.repository

import com.alberto.calorietracker.home.domain.model.Food


interface FoodRepository {
    suspend fun searchFood(query: String): List<Food>
    suspend fun getFoodById(id: String): Result<Food>
}
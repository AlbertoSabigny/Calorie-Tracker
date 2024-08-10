package com.alberto.calorietracker.home.domain.usecase

import com.alberto.calorietracker.home.domain.model.Food
import com.alberto.calorietracker.home.domain.repository.FoodRepository
import javax.inject.Inject

class SearchFoodUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    suspend operator fun invoke(query: String): List<Food> {
        return repository.searchFood(query)
    }
}
package com.alberto.calorietracker.home.domain.usecase

import com.alberto.calorietracker.home.data.local.entity.FoodEntity
import com.alberto.calorietracker.home.domain.repository.FoodRepository
import com.alberto.calorietracker.home.presentation.detail.MealType
import javax.inject.Inject

class GetFoodConsumptionByDateUseCase  @Inject constructor(
    private val repository: FoodRepository
) {
    suspend operator fun invoke(date: Long, mealType: MealType): List<FoodEntity> {
        return repository.getFoodConsumptionByDate(date, mealType)
    }
}
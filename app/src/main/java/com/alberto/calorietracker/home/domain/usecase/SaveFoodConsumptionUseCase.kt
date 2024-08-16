package com.alberto.calorietracker.home.domain.usecase

import com.alberto.calorietracker.home.data.local.entity.FoodEntity
import com.alberto.calorietracker.home.domain.repository.FoodRepository
import javax.inject.Inject

class SaveFoodConsumptionUseCase @Inject constructor(
    private val repository: FoodRepository
) {
    suspend operator fun invoke(foodEntity: FoodEntity) {
        repository.saveFoodConsumption(foodEntity)
    }
}
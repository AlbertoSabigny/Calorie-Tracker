package com.alberto.calorietracker.home.data.repository

import com.alberto.calorietracker.home.data.mapper.toDomain
import com.alberto.calorietracker.home.data.mapper.toDomainList
import com.alberto.calorietracker.home.data.remote.source.RemoteFoodDataSource
import com.alberto.calorietracker.home.domain.model.Food
import com.alberto.calorietracker.home.domain.repository.FoodRepository
import javax.inject.Inject

class FoodRepositoryimpl @Inject constructor(
    private val remoteDataSource: RemoteFoodDataSource
) : FoodRepository {

    override suspend fun searchFood(query: String): List<Food> {
        val foodResponse = remoteDataSource.buscarAlimentos(query)
        return foodResponse.toDomainList()
    }

    override suspend fun getFoodById(id: String): Result<Food> {
        return try {
            val foodResponse = remoteDataSource.obtenerAlimentoPorId(id)
            if (foodResponse != null) {
                Result.success(foodResponse.toDomain())
            } else {
                Result.failure(Exception("Alimento no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}

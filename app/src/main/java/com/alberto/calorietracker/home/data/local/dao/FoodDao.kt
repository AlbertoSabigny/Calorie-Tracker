package com.alberto.calorietracker.home.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alberto.calorietracker.home.data.local.entity.FoodEntity
import com.alberto.calorietracker.home.presentation.detail.MealType

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodConsumption(foodConsumption: FoodEntity)

    @Query("SELECT * FROM food_table WHERE date = :date AND mealType = :mealType")
    suspend fun getFoodConsumptionByDate(date: Long, mealType: MealType): List<FoodEntity>

    @Query("DELETE FROM food_table WHERE id = :id")
    suspend fun deleteFoodConsumption(id: Int)
}
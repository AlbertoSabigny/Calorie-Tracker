package com.alberto.calorietracker.home.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alberto.calorietracker.home.presentation.detail.MealType

@Entity(tableName = "food_table")
data class FoodEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "date")val date: Long,
    @ColumnInfo(name = "mealType")val mealType: MealType,
    @ColumnInfo(name = "amount")val amount: Int,
    @ColumnInfo(name = "calories")val calories: Double,
    @ColumnInfo(name = "proteins")val proteins: Double,
    @ColumnInfo(name = "carbohydrates") val carbohydrates: Double,
    @ColumnInfo(name = "fats")val fats: Double
)
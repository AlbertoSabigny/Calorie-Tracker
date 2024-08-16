package com.alberto.calorietracker.home.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alberto.calorietracker.home.data.local.dao.FoodDao
import com.alberto.calorietracker.home.data.local.entity.FoodEntity

@Database(entities = [FoodEntity::class], version = 2)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun getFoodDao(): FoodDao
}
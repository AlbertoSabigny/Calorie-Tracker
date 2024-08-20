package com.alberto.calorietracker.home.data.mapper

import com.alberto.calorietracker.home.data.local.entity.FoodEntity
import com.alberto.calorietracker.home.data.remote.model.FoodResponse
import com.alberto.calorietracker.home.data.remote.model.NutrientsResponse
import com.alberto.calorietracker.home.domain.model.Food
import com.alberto.calorietracker.home.domain.model.FoodConsumed
import com.alberto.calorietracker.home.domain.model.Nutrients
import java.time.LocalDate

fun FoodResponse.toDomain(): Food {
    return Food(
        id = this.id,
        nombre = this.nombre,
        categoria = this.categoria,
        unidadBase = this.unidadBase,
        cantidadBase = this.cantidadBase,
        unidadPorcion = this.unidadPorcion,
        tamanioPorcion = this.tamanioPorcion,
        nutrientes = this.nutrientes.map { it.toDomain() }
    )
}

fun NutrientsResponse.toDomain(): Nutrients {
    return Nutrients(
        id = this.id,
        energia = this.energia,
        proteinas = this.proteinas,
        lipidos = this.lipidos,
        carbohidratos = this.carbohidratos
    )
}

fun List<FoodResponse>.toDomainList(): List<Food> {
    return this.map { it.toDomain() }
}

fun FoodEntity.toFoodConsumed(): FoodConsumed {
    return FoodConsumed(
        id = this.id,
        firebaseId = this.firebaseId,
        nombre = this.nombre,
        date = LocalDate.ofEpochDay(this.date),
        mealType = this.mealType,
        amount = this.amount,
        calories = this.calories,
        proteins = this.proteins,
        carbohydrates = this.carbohydrates,
        fats = this.fats,
        measurementType = measurementType
    )
}
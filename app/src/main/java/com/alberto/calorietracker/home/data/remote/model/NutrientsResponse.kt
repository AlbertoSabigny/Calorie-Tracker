package com.alberto.calorietracker.home.data.remote.model

data class NutrientsResponse (
    val id: String = "",
    val energia: Double = 0.0,
    val proteinas: Double = 0.0,
    val lipidos: Double = 0.0,
    val carbohidratos: Double = 0.0
)
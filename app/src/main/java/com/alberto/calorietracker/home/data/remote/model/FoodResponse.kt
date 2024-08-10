package com.alberto.calorietracker.home.data.remote.model

data class FoodResponse (
    val id: String = "",
    val nombre: String = "",
    val categoria: String = "",
    val unidadBase: String = "",
    val cantidadBase: Int = 0,
    val nutrientes: List<NutrientsResponse> = emptyList()
)
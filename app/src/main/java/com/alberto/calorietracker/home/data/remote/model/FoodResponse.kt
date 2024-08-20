package com.alberto.calorietracker.home.data.remote.model

data class FoodResponse (
    val id: String = "",
    val nombre: String = "",
    val categoria: String = "",
    val unidadBase: String = "",
    val cantidadBase: Int = 0,
    val tamanioPorcion: Double = 0.0,
    val unidadPorcion: String = "",
    val nutrientes: List<NutrientsResponse> = emptyList()
)
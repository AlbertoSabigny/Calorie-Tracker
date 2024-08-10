package com.alberto.calorietracker.home.domain.model

data class Food (
    val id: String,
    val nombre: String,
    val categoria: String,
    val unidadBase: String,
    val cantidadBase: Int,
    val nutrientes: List<Nutrients>
)
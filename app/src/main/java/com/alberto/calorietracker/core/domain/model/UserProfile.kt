package com.alberto.calorietracker.core.domain.model

data class UserProfile(
    val age: String,
    val height: String,
    val weight: String,
    val gender: String,
    val activityLevel: String,
    val targetWeight: String,
    val goalSpeed: String,
    val imc: Double,
    val pesoIdealMinimo: Double,
    val pesoIdealMaximo: Double,
    val caloriasDiariasRecomendadas: Double
)
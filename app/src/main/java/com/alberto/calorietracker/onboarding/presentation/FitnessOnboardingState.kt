package com.alberto.calorietracker.onboarding.presentation

import com.alberto.calorietracker.onboarding.domain.model.ActivityLevel

data class FitnessOnboardingState(
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val gender: String = "",
    val activityLevel: String = "Sedentario",
    val targetWeight: String = "",
    val goalSpeed: String = "",
    val imc: Double = 0.0,
    val pesoIdealMinimo: Double = 0.0,
    val pesoIdealMaximo: Double = 0.0,
    val caloriasDiariasRecomendadas: Double = 0.0,
    val hasCompletedOnboarding: Boolean = false,
    val activityLevels: List<ActivityLevel> = emptyList()
)
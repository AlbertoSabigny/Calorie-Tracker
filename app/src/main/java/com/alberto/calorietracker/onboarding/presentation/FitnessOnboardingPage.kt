package com.alberto.calorietracker.onboarding.presentation

import androidx.compose.runtime.Composable

data class FitnessOnboardingPage(
    val title: String,
    val subtitle: String,
    val content: @Composable () -> Unit
)
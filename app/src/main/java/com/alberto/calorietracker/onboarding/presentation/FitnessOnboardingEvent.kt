package com.alberto.calorietracker.onboarding.presentation

sealed class FitnessOnboardingEvent {
    data class UpdateAge(val age: String) : FitnessOnboardingEvent()
    data class UpdateHeight(val height: String) : FitnessOnboardingEvent()
    data class UpdateWeight(val weight: String) : FitnessOnboardingEvent()
    data class UpdateGender(val gender: String) : FitnessOnboardingEvent()
    data class UpdateActivityLevel(val activityLevel: String) : FitnessOnboardingEvent()
    data class UpdateTargetWeight(val targetWeight: String) : FitnessOnboardingEvent()
    data class UpdateGoalSpeed(val goalSpeed: String) : FitnessOnboardingEvent()
    object CalculateIMCAndIdealWeight : FitnessOnboardingEvent()
    object CalculateDailyCalories : FitnessOnboardingEvent()
    object SaveUserData : FitnessOnboardingEvent()
}
package com.alberto.calorietracker.onboarding.domain.repository

import com.alberto.calorietracker.core.domain.model.UserProfile

interface OnboardingRepository {
    suspend fun saveUserProfile(userProfile: UserProfile)
}
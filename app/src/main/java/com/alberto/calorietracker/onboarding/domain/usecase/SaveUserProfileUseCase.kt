package com.alberto.calorietracker.onboarding.domain.usecase

import com.alberto.calorietracker.core.domain.model.UserProfile
import com.alberto.calorietracker.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    suspend operator fun invoke(userProfile: UserProfile) {
        onboardingRepository.saveUserProfile(userProfile)
    }
}
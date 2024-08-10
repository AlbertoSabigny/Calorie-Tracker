package com.alberto.calorietracker.onboarding.domain.usecase

import com.alberto.calorietracker.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class CompleteOnboardingUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(){
        repository.completeOnboarding()
    }
}
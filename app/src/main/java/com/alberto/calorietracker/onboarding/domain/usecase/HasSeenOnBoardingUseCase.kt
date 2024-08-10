package com.alberto.calorietracker.onboarding.domain.usecase

import com.alberto.calorietracker.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class HasSeenOnBoardingUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Boolean{
        return repository.hasSeenOnboarding()
    }
}
package com.alberto.calorietracker.profile.domain.usecase

import com.alberto.calorietracker.core.domain.model.UserProfile

import com.alberto.calorietracker.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): UserProfile? {
        return repository.getUserProfile()
    }
}
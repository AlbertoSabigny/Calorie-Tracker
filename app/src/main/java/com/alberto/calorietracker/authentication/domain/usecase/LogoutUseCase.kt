package com.alberto.calorietracker.authentication.domain.usecase

import com.alberto.calorietracker.authentication.domain.repository.AuthenticationRepository

class LogoutUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}
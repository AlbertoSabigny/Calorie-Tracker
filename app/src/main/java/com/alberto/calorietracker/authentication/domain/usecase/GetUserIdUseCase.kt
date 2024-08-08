package com.alberto.calorietracker.authentication.domain.usecase

import com.alberto.calorietracker.authentication.domain.repository.AuthenticationRepository

class GetUserIdUseCase(private val repository: AuthenticationRepository) {
    operator fun invoke(): String? {
        return repository.getUserId()
    }
}
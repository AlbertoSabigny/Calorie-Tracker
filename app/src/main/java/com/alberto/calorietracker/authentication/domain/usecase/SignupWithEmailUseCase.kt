package com.alberto.calorietracker.authentication.domain.usecase

import com.alberto.calorietracker.authentication.domain.repository.AuthenticationRepository

class SignupWithEmailUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return repository.signup(email, password)
    }
}
package com.alberto.calorietracker.authentication.domain.usecase

import com.alberto.calorietracker.authentication.domain.matcher.EmailMatcher

class ValidateEmailUseCase(private val emailMatcher: EmailMatcher) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.isValid(email)
    }
}
package com.alberto.calorietracker.authentication.domain.usecase

data class SignupUseCases(
    val signupWithEmailUseCase: SignupWithEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase
)
package com.alberto.calorietracker.authentication.presentation.signup

sealed interface SignupEvent {
    data class EmailChange(val email: String) : SignupEvent
    data class PasswordChange(val password: String) : SignupEvent
    object LogIn : SignupEvent
    object SignUp : SignupEvent
}

package com.alberto.calorietracker.authentication.domain.matcher

interface EmailMatcher {
    fun isValid(email: String): Boolean
}
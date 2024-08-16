package com.alberto.calorietracker.profile.presentation

import com.alberto.calorietracker.core.domain.model.UserProfile


data class ProfileUiState(
    val userProfile: UserProfile? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val editableAge: String = "",
    val editableHeight: String = "",
    val editableWeight: String = "",
    val editableGender: String = "",
    val editableActivityLevel: String = "",
    val editableTargetWeight: String = "",
    val editableGoalSpeed: String = ""
)
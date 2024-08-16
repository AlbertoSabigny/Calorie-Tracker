package com.alberto.calorietracker.profile.presentation

sealed class ProfileEvent {
    object LoadUserProfile : ProfileEvent()
    data class ShowError(val message: String) : ProfileEvent()
    data class OnAgeChanged(val newAge: String) : ProfileEvent()
    data class OnHeightChanged(val newHeight: String) : ProfileEvent()
    data class OnWeightChanged(val newWeight: String) : ProfileEvent()
    data class OnGenderChanged(val newGender: String) : ProfileEvent()
    data class OnActivityLevelChanged(val newActivityLevel: String) : ProfileEvent()
    data class OnTargetWeightChanged(val newTargetWeight: String) : ProfileEvent()
    data class OnGoalSpeedChanged(val newGoalSpeed: String) : ProfileEvent()
    object SaveUserProfile : ProfileEvent()
}
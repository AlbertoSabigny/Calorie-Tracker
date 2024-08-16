package com.alberto.calorietracker.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alberto.calorietracker.core.domain.model.UserProfile
import com.alberto.calorietracker.onboarding.domain.usecase.SaveUserProfileUseCase
import com.alberto.calorietracker.profile.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state

    init {
        onEvent(ProfileEvent.LoadUserProfile)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LoadUserProfile -> loadUserProfile()
            is ProfileEvent.ShowError -> _state.value = _state.value.copy(errorMessage = event.message)
            is ProfileEvent.OnAgeChanged -> _state.value = _state.value.copy(editableAge = event.newAge)
            is ProfileEvent.OnHeightChanged -> _state.value = _state.value.copy(editableHeight = event.newHeight)
            is ProfileEvent.OnWeightChanged -> _state.value = _state.value.copy(editableWeight = event.newWeight)
            is ProfileEvent.OnGenderChanged -> _state.value = _state.value.copy(editableGender = event.newGender)
            is ProfileEvent.OnActivityLevelChanged -> _state.value = _state.value.copy(editableActivityLevel = event.newActivityLevel)
            is ProfileEvent.OnTargetWeightChanged -> _state.value = _state.value.copy(editableTargetWeight = event.newTargetWeight)
            is ProfileEvent.OnGoalSpeedChanged -> _state.value = _state.value.copy(editableGoalSpeed = event.newGoalSpeed)
            is ProfileEvent.SaveUserProfile -> saveUserProfile()
        }
    }

    private fun loadUserProfile() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val userProfile = getUserProfileUseCase()
                if (userProfile != null) {
                    _state.value = _state.value.copy(
                        userProfile = userProfile,
                        isLoading = false,
                        editableAge = userProfile.age,
                        editableHeight = userProfile.height,
                        editableWeight = userProfile.weight,
                        editableGender = userProfile.gender,
                        editableActivityLevel = userProfile.activityLevel,
                        editableTargetWeight = userProfile.targetWeight,
                        editableGoalSpeed = userProfile.goalSpeed
                    )
                }
            } catch (e: Exception) {
                onEvent(ProfileEvent.ShowError("Failed to load profile"))
            }
        }
    }

    private fun saveUserProfile() {
        val currentState = _state.value
        val updatedUserProfile = UserProfile(
            age = currentState.editableAge,
            height = currentState.editableHeight,
            weight = currentState.editableWeight,
            gender = currentState.editableGender,
            activityLevel = currentState.editableActivityLevel,
            targetWeight = currentState.editableTargetWeight,
            goalSpeed = currentState.editableGoalSpeed,
            imc = currentState.userProfile?.imc ?: 0.0, // Calcula o usa el existente
            pesoIdealMinimo = currentState.userProfile?.pesoIdealMinimo ?: 0.0,
            pesoIdealMaximo = currentState.userProfile?.pesoIdealMaximo ?: 0.0,
            caloriasDiariasRecomendadas = currentState.userProfile?.caloriasDiariasRecomendadas ?: 0.0
        )
        viewModelScope.launch {
            try {
                saveUserProfileUseCase(updatedUserProfile)
                _state.value = _state.value.copy(userProfile = updatedUserProfile)
            } catch (e: Exception) {
                onEvent(ProfileEvent.ShowError("Failed to save profile"))
            }
        }
    }
}
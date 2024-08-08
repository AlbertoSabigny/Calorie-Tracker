package com.alberto.calorietracker.onboarding.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alberto.calorietracker.core.domain.model.UserProfile
import com.alberto.calorietracker.onboarding.domain.model.ActivityLevel
import com.alberto.calorietracker.onboarding.domain.usecase.GetActivityLevelsUseCase
import com.alberto.calorietracker.onboarding.domain.usecase.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FitnessOnboardingViewModel @Inject constructor(
    private val getActivityLevelsUseCase: GetActivityLevelsUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FitnessOnboardingState())
    val state: StateFlow<FitnessOnboardingState> = _state

    init {
        _state.update { it.copy(activityLevels = getActivityLevelsUseCase()) }
    }

    fun onEvent(event: FitnessOnboardingEvent) {
        when (event) {
            is FitnessOnboardingEvent.UpdateAge -> updateAge(event.age)
            is FitnessOnboardingEvent.UpdateHeight -> updateHeight(event.height)
            is FitnessOnboardingEvent.UpdateWeight -> updateWeight(event.weight)
            is FitnessOnboardingEvent.UpdateGender -> updateGender(event.gender)
            is FitnessOnboardingEvent.UpdateActivityLevel -> updateActivityLevel(event.activityLevel)
            is FitnessOnboardingEvent.UpdateTargetWeight -> updateTargetWeight(event.targetWeight)
            is FitnessOnboardingEvent.UpdateGoalSpeed -> updateGoalSpeed(event.goalSpeed)
            is FitnessOnboardingEvent.CalculateIMCAndIdealWeight -> calcularIMCYPesoIdeal()
            is FitnessOnboardingEvent.CalculateDailyCalories -> calcularCaloriasDiarias()
            is FitnessOnboardingEvent.SaveUserData -> saveUserData()
            is FitnessOnboardingEvent.CompleteOnboarding -> completeOnboarding()
        }
    }

    private fun updateAge(newAge: String) {
        _state.update { it.copy(age = newAge) }
    }

    private fun updateHeight(newHeight: String) {
        _state.update { it.copy(height = newHeight) }
    }

    private fun updateWeight(newWeight: String) {
        _state.update { it.copy(weight = newWeight) }
    }

    private fun updateGender(newGender: String) {
        _state.update { it.copy(gender = newGender) }
    }

    private fun updateActivityLevel(newActivityLevel: String) {
        _state.update { it.copy(activityLevel = newActivityLevel) }
      //  calcularCaloriasDiarias()
    }

    private fun updateTargetWeight(newTargetWeight: String) {
        _state.update { it.copy(targetWeight = newTargetWeight) }
    }

    private fun updateGoalSpeed(newGoalSpeed: String) {
        _state.update { it.copy(goalSpeed = newGoalSpeed) }
    }

    private fun calcularIMC(peso: Double, altura: Double): Double {
        return peso / (altura * altura)
    }

    private fun calcularPesoIdealMinimo(altura: Double): Double {
        val imcMinimo = 18.5
        return imcMinimo * (altura * altura)
    }

    private fun calcularPesoIdealMaximo(altura: Double): Double {
        val imcMaximo = 24.9
        return imcMaximo * (altura * altura)
    }

    private fun calcularIMCYPesoIdeal() {
        val peso = state.value.weight.toDoubleOrNull()
        val alturaCm = state.value.height.toDoubleOrNull()
        val altura = alturaCm?.div(100) // Convertir altura de cm a metros
        if (peso != null && altura != null && altura > 0) {
            val imc = calcularIMC(peso, altura)
            val pesoIdealMinimo = calcularPesoIdealMinimo(altura)
            val pesoIdealMaximo = calcularPesoIdealMaximo(altura)
            _state.update { it.copy(
                imc = imc,
                pesoIdealMinimo = pesoIdealMinimo,
                pesoIdealMaximo = pesoIdealMaximo
            ) }
        }
    }

    private fun calcularTMB(): Double {
        val peso = state.value.weight.toDoubleOrNull()
        val alturaCm = state.value.height.toDoubleOrNull()
        val edad = state.value.age.toIntOrNull()
        if (peso != null && alturaCm != null && edad != null) {
            return if (state.value.gender == "Hombre") {
                88.362 + (13.397 * peso) + (4.799 * alturaCm) - (5.677 * edad)
            } else {
                447.593 + (9.247 * peso) + (3.098 * alturaCm) - (4.330 * edad)
            }
        }
        return 0.0
    }

    private fun calcularCaloriasDiarias() {
        val tmb = calcularTMB()
        var caloriasDiarias = when (state.value.activityLevel) {
            "Sedentario" -> tmb * 1.2
            "Ligero" -> tmb * 1.375
            "Moderado" -> tmb * 1.55
            "Intenso" -> tmb * 1.725
            "Muy Intenso" -> tmb * 1.9
            else -> tmb
        }

        val ajusteCalorico = when (state.value.goalSpeed) {
            "Lento" -> if (state.value.weight > state.value.targetWeight) -250 else 250
            "Medio" -> if (state.value.weight > state.value.targetWeight) -500 else 500
            "Rápido" -> if (state.value.weight > state.value.targetWeight) -750 else 750
            else -> 0
        }

        caloriasDiarias += ajusteCalorico
        _state.update { it.copy(caloriasDiariasRecomendadas = caloriasDiarias) }
    }

    private fun saveUserData() {
        viewModelScope.launch {
            val currentState = _state.value
            val userProfile = UserProfile(
                age = currentState.age,
                height = currentState.height,
                weight = currentState.weight,
                gender = currentState.gender,
                activityLevel = currentState.activityLevel,
                targetWeight = currentState.targetWeight,
                goalSpeed = currentState.goalSpeed,
                imc = currentState.imc,
                pesoIdealMinimo = currentState.pesoIdealMinimo,
                pesoIdealMaximo = currentState.pesoIdealMaximo,
                caloriasDiariasRecomendadas = currentState.caloriasDiariasRecomendadas
            )
            saveUserProfileUseCase(userProfile)

        }
    }

    private fun completeOnboarding() {
        _state.update { it.copy(hasCompletedOnboarding = true) }
    }
}
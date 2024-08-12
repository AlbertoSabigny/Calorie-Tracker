package com.alberto.calorietracker.home.presentation.detail

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alberto.calorietracker.home.domain.usecase.GetFoodByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val getFoodByIdUseCase: GetFoodByIdUseCase,
   // private val saveFoodConsumptionUseCase: SaveFoodConsumptionUseCase, // Nueva dependencia
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodDetailUiState())
    val uiState: StateFlow<FoodDetailUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<FoodDetailEvent>()

    init {
        viewModelScope.launch {
            _events.collect { event ->
                when (event) {
                    is FoodDetailEvent.LoadFood -> loadFood(event.id)
                    is FoodDetailEvent.UpdateCustomAmount -> updateCustomAmount(event.amount)
                    is FoodDetailEvent.SaveFoodConsumption -> saveFoodConsumption()
                    is FoodDetailEvent.UpdateMealType -> updateMealType(event.mealType)
                }
            }
        }

        savedStateHandle.get<String>("foodId")?.let { foodId ->
            loadFood(foodId)
        }
    }

    private fun updateMealType(mealType: MealType) {
        _uiState.update { it.copy(selectedMealType = mealType) }
    }

    fun onEvent(event: FoodDetailEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    private fun loadFood(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val result = getFoodByIdUseCase(id)
                result.fold(
                    onSuccess = { food ->
                        _uiState.update {
                            it.copy(
                                food = food,
                                isLoading = false,
                                error = null,
                                customAmount = food.cantidadBase.toString() // Inicializar con la cantidad base
                            )
                        }
                        Log.d(TAG, "Alimento cargado: ${food.nombre}")
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                error = error.message,
                                isLoading = false
                            )
                        }
                        Log.e(TAG, "Error al cargar el alimento: ${error.message}", error)
                    }
                )
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
                Log.e(TAG, "Excepción al cargar el alimento: ${e.message}", e)
            }
        }
    }

    private fun updateCustomAmount(amount: String) {
        _uiState.update { it.copy(customAmount = amount) }
    }

    private fun saveFoodConsumption() {
        val currentState = _uiState.value
        Log.d(TAG, "Se guardará alimento: ${currentState.food?.nombre}, " +
                "Cantidad: ${currentState.customAmount}, " +
                "Tipo de comida: ${currentState.selectedMealType}")
        // Simulamos un guardado exitoso
        _uiState.update { it.copy(saveSuccess = true) }
        // Reseteamos el estado de éxito después de un breve delay
        viewModelScope.launch {
            delay(2000) // 2 segundos
            _uiState.update { it.copy(saveSuccess = false) }
        }
    }
}


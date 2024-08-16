package com.alberto.calorietracker.home.presentation.detail

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alberto.calorietracker.home.data.local.entity.FoodEntity
import com.alberto.calorietracker.home.domain.usecase.GetFoodByIdUseCase
import com.alberto.calorietracker.home.domain.usecase.SaveFoodConsumptionUseCase
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
    private val saveFoodConsumptionUseCase: SaveFoodConsumptionUseCase,
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
                    is FoodDetailEvent.SaveFoodConsumption -> saveFoodConsumption(event.date)
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
                                customAmount = food.cantidadBase.toString()
                            )
                        }
                        updateNutritionInfo(food.cantidadBase)
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
        updateNutritionInfo(amount.toIntOrNull() ?: return)
    }

    private fun updateNutritionInfo(customAmount: Int) {
        val food = _uiState.value.food ?: return
        val baseAmount = food.cantidadBase

        _uiState.update { currentState ->
            currentState.copy(
                calories = calculateNutrient(food.nutrientes.first().energia, baseAmount, customAmount),
                proteins = calculateNutrient(food.nutrientes.first().proteinas, baseAmount, customAmount),
                carbohydrates = calculateNutrient(food.nutrientes.first().carbohidratos, baseAmount, customAmount),
                fats = calculateNutrient(food.nutrientes.first().lipidos, baseAmount, customAmount)
            )
        }
    }

    private fun saveFoodConsumption(date: Long) {
        val currentState = _uiState.value
        val food = currentState.food ?: return
        val customAmount = currentState.customAmount.toIntOrNull() ?: return

        viewModelScope.launch {
            try {
                val foodEntity = FoodEntity(
                    nombre = food.nombre,
                    date = date,
                    mealType = currentState.selectedMealType,
                    amount = customAmount,
                    calories = currentState.calories,
                    proteins = currentState.proteins,
                    carbohydrates = currentState.carbohydrates,
                    fats = currentState.fats
                )

                saveFoodConsumptionUseCase(foodEntity)
                _uiState.update { it.copy(saveSuccess = true) }

                // Reseteamos el estado de éxito después de un breve delay
                delay(2000) // 2 segundos
                _uiState.update { it.copy(saveSuccess = false) }

                Log.d(TAG, "Alimento guardado con éxito: ${food.nombre}")
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
                Log.e(TAG, "Error al guardar el consumo de alimento: ${e.message}", e)
            }
        }
    }

    private fun calculateNutrient(baseValue: Double, baseAmount: Int, customAmount: Int): Double {
        return baseValue * customAmount / baseAmount
    }
}
package com.alberto.calorietracker.home.presentation.detail

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alberto.calorietracker.home.data.local.entity.FoodEntity
import com.alberto.calorietracker.home.data.local.entity.MeasurementType
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
                    is FoodDetailEvent.UpdateMeasurementType -> updateMeasurementType(event.type)
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

    private fun updateMeasurementType(type: MeasurementType) {
        _uiState.update { it.copy(
            selectedMeasurementType = type,
            customAmount = if (type == MeasurementType.PORTION) "1" else it.food?.cantidadBase.toString()
        ) }
        updateNutritionInfo()
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
                                customAmount = food.cantidadBase.toString(),
                                selectedMeasurementType = MeasurementType.GRAM,
                                unidadBase = food.unidadBase,
                                unidadPorcion = food.unidadPorcion
                            )
                        }
                        updateNutritionInfo()
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
        updateNutritionInfo()
    }

    private fun updateNutritionInfo() {
        val food = _uiState.value.food ?: return
        val customAmount = _uiState.value.customAmount.toDoubleOrNull() ?: return
        val baseAmount = food.cantidadBase.toDouble()

        val factor = when (_uiState.value.selectedMeasurementType) {
            MeasurementType.GRAM -> customAmount / baseAmount
            MeasurementType.PORTION -> customAmount * (food.tamanioPorcion / baseAmount)
        }

        _uiState.update { currentState ->
            currentState.copy(
                calories = calculateNutrient(food.nutrientes.first().energia, factor),
                proteins = calculateNutrient(food.nutrientes.first().proteinas, factor),
                carbohydrates = calculateNutrient(food.nutrientes.first().carbohidratos, factor),
                fats = calculateNutrient(food.nutrientes.first().lipidos, factor)
            )
        }
    }

    private fun saveFoodConsumption(date: Long) {
        val currentState = _uiState.value
        val food = currentState.food ?: return
        val customAmount = currentState.customAmount.toDoubleOrNull() ?: return

        viewModelScope.launch {
            try {
                val amountInGrams = when (currentState.selectedMeasurementType) {
                    MeasurementType.GRAM -> customAmount
                    MeasurementType.PORTION -> customAmount * food.tamanioPorcion
                }

                val foodEntity = FoodEntity(
                    firebaseId = food.id,
                    nombre = food.nombre,
                    date = date,
                    mealType = currentState.selectedMealType,
                    amount = amountInGrams,
                    measurementType = currentState.selectedMeasurementType,
                    calories = currentState.calories,
                    proteins = currentState.proteins,
                    carbohydrates = currentState.carbohydrates,
                    fats = currentState.fats
                )

                saveFoodConsumptionUseCase(foodEntity)
                _uiState.update { it.copy(saveSuccess = true) }

                delay(2000)
                _uiState.update { it.copy(saveSuccess = false) }

                Log.d(TAG, "Alimento guardado con éxito: ${food.nombre}")
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
                Log.e(TAG, "Error al guardar el consumo de alimento: ${e.message}", e)
            }
        }
    }

    private fun calculateNutrient(baseValue: Double, factor: Double): Double {
        return baseValue * factor
    }
}
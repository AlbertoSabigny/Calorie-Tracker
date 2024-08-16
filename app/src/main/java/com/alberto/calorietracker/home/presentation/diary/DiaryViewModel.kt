package com.alberto.calorietracker.home.presentation.diary
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alberto.calorietracker.home.data.mapper.toFoodConsumed
import com.alberto.calorietracker.home.domain.model.FoodConsumed
import com.alberto.calorietracker.home.domain.usecase.GetFoodConsumptionByDateUseCase
import com.alberto.calorietracker.home.presentation.detail.MealType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val getFoodConsumptionByDateUseCase: GetFoodConsumptionByDateUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DiaryUiState())
    val state: StateFlow<DiaryUiState> = _state.asStateFlow()

    init {
        loadFoodConsumption(LocalDate.now())
    }

    fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.UpdateSelectedDate -> updateSelectedDate(event.newDate)
            is DiaryEvent.NavigateToPreviousDay -> navigateToPreviousDay()
            is DiaryEvent.NavigateToNextDay -> navigateToNextDay()
        }
    }

    private fun updateSelectedDate(newDate: LocalDate) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                selectedDate = newDate,
                topBarText = updateTopBarText(newDate)
            )
            loadFoodConsumption(newDate)
        }
    }

    private fun navigateToPreviousDay() {
        updateSelectedDate(_state.value.selectedDate.minusDays(1))
    }

    private fun navigateToNextDay() {
        updateSelectedDate(_state.value.selectedDate.plusDays(1))
    }

    private fun updateTopBarText(date: LocalDate): String {
        val today = LocalDate.now()
        return when {
            date.isEqual(today) -> "Hoy"
            date.isEqual(today.plusDays(1)) -> "Mañana"
            date.isEqual(today.minusDays(1)) -> "Ayer"
            else -> date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
        }
    }

    private fun loadFoodConsumption(date: LocalDate) {
        viewModelScope.launch {
            val dateInEpochDays = date.toEpochDay()
            val foodConsumed = listOf(
                getFoodConsumptionByDateUseCase(dateInEpochDays, MealType.BREAKFAST),
                getFoodConsumptionByDateUseCase(dateInEpochDays, MealType.LUNCH),
                getFoodConsumptionByDateUseCase(dateInEpochDays, MealType.DINNER)
            ).flatten().map { it.toFoodConsumed() }

            val nutrientSums = calculateNutrientSums(foodConsumed)

            _state.value = _state.value.copy(
                breakfastFood = foodConsumed.filter { it.mealType == MealType.BREAKFAST },
                lunchFood = foodConsumed.filter { it.mealType == MealType.LUNCH },
                dinnerFood = foodConsumed.filter { it.mealType == MealType.DINNER },
                carbs = nutrientSums.carbs,
                proteins = nutrientSums.proteins,
                fats = nutrientSums.fats,
                calories = nutrientSums.calories
            )
        }
    }

    private fun calculateNutrientSums(foodList: List<FoodConsumed>): NutrientSums {
        val totalCarbs = foodList.sumOf { it.carbohydrates }
        val totalProteins = foodList.sumOf { it.proteins }
        val totalFats = foodList.sumOf { it.fats }
        val totalCalories = foodList.sumOf { it.calories }

        return NutrientSums(
            carbs = totalCarbs.toInt(),
            proteins = totalProteins.toInt(),
            fats = totalFats.toInt(),
            calories = totalCalories.toInt()
        )
    }
}

data class NutrientSums(
    val carbs: Int,
    val proteins: Int,
    val fats: Int,
    val calories: Int
)


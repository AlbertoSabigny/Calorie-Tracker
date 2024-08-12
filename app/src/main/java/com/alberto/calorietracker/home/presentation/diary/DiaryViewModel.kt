package com.alberto.calorietracker.home.presentation.diary
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class DiaryViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(DiaryUiState())
    val state: StateFlow<DiaryUiState> = _state.asStateFlow()

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
}


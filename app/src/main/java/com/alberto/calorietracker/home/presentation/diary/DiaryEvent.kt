package com.alberto.calorietracker.home.presentation.diary

import java.time.LocalDate

sealed class DiaryEvent {
    data class UpdateSelectedDate(val newDate: LocalDate) : DiaryEvent()
    object NavigateToPreviousDay : DiaryEvent()
    object NavigateToNextDay : DiaryEvent()
}
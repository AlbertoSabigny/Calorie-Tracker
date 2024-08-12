package com.alberto.calorietracker.home.presentation.search

import java.time.LocalDate

sealed class SearchFoodEvent {
    data class UpdateSearchQuery(val query: String) : SearchFoodEvent()

}
package com.alberto.calorietracker.home.presentation.search

sealed class SearchFoodEvent {
    data class UpdateSearchQuery(val query: String) : SearchFoodEvent()
}
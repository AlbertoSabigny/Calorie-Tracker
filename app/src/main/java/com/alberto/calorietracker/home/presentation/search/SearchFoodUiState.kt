package com.alberto.calorietracker.home.presentation.search

import com.alberto.calorietracker.home.domain.model.Food

data class SearchFoodUiState(
    val searchQuery: String = "",
    val alimentos: List<Food> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
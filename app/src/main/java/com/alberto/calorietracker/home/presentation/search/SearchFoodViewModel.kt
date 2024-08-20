package com.alberto.calorietracker.home.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alberto.calorietracker.home.domain.model.Food
import com.alberto.calorietracker.home.domain.usecase.SearchFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchFoodViewModel"
@HiltViewModel
class SearchFoodViewModel @Inject constructor(
    private val searchFoodUseCase: SearchFoodUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchFoodUiState())
    val uiState: StateFlow<SearchFoodUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SearchFoodEvent>()

    init {
        viewModelScope.launch {
            _events.collect { event ->
                when (event) {
                    is SearchFoodEvent.UpdateSearchQuery -> handleSearchQuery(event.query)
                }
            }
        }

        viewModelScope.launch {
            _uiState
                .map { it.searchQuery }
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        if (query.isBlank()) {
                            Log.d(TAG, "Búsqueda vacía, limpiando resultados")
                            emit(emptyList())
                        } else {
                            Log.d(TAG, "Iniciando búsqueda para: $query")
                            _uiState.update { it.copy(isLoading = true, error = null) }
                            emit(searchFoodUseCase(query))
                        }
                    }
                }
                .catch { e ->
                    Log.e(TAG, "Error durante la búsqueda: ${e.message}", e)
                    _uiState.update { it.copy(error = e.message ?: "Error desconocido", isLoading = false) }
                }
                .collect { alimentos ->
                    Log.d(TAG, "Resultados obtenidos: ${alimentos.size} alimentos")
                    if (alimentos.isEmpty()) {
                        Log.d(TAG, "No se encontraron resultados")
                    } else {
                        alimentos.forEach { food ->
                            Log.d(TAG, "Alimento: ${food.nombre}, Nutrientes: ${food.nutrientes.size}")
                            food.nutrientes.forEach { nutriente ->
                                Log.d(TAG, "  Nutriente: id=${nutriente.id}, energia=${nutriente.energia}, proteinas=${nutriente.proteinas}, lipidos=${nutriente.lipidos}, carbohidratos=${nutriente.carbohidratos}")
                            }
                        }
                    }
                    _uiState.update { it.copy(alimentos = alimentos, isLoading = false, error = null) }
                }
        }
    }

    fun onEvent(event: SearchFoodEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    private fun handleSearchQuery(query: String) {
        Log.d(TAG, "Actualizando query de búsqueda: $query")
        _uiState.update { it.copy(searchQuery = query) }
    }
}


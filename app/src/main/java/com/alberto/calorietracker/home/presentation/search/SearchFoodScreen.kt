package com.alberto.calorietracker.home.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alberto.calorietracker.home.domain.model.Food
import com.alberto.calorietracker.ui.theme.OrangeMedium
import com.alberto.calorietracker.ui.theme.VeryDarkGray
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun SearchFoodScreen(
    date: LocalDate,
    viewModel: SearchFoodViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onFoodSelected: (String, LocalDate) ->Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar Alimentos", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                backgroundColor = Color.Black
            )
        },
        backgroundColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(padding)
                .padding(16.dp)
        ) {

            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = { newQuery ->
                    viewModel.onEvent(SearchFoodEvent.UpdateSearchQuery(newQuery))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchContent(uiState = uiState, onFoodSelected = { foodId -> onFoodSelected(foodId, date) })
        }
    }
}

@Composable
fun SearchContent(uiState: SearchFoodUiState, onFoodSelected: (String) -> Unit) {
    when {
        uiState.isLoading -> LoadingIndicator()
        uiState.error != null -> ErrorMessage(uiState.error)
        uiState.alimentos.isEmpty() -> EmptySearchResult()
        else -> FoodList(foods = uiState.alimentos, onFoodSelected = onFoodSelected)
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Busca un alimento...", color = VeryDarkGray) },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        leadingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Icono de búsqueda", tint = Color.White)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.Gray,
            backgroundColor = Color.DarkGray
        ),
        singleLine = true
    )
}

@Composable
fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = OrangeMedium)
    }
}

@Composable
fun ErrorMessage(error: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Error: $error",
            color = Color.Red,
            fontSize = 18.sp
        )
    }
}

@Composable
fun EmptySearchResult() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            "No se encontraron resultados",
            color = Color.Gray,
            fontSize = 18.sp
        )
    }
}

@Composable
fun FoodList(foods: List<Food>, onFoodSelected: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(foods) { food ->
            FoodListItem(food = food,
                onFoodSelected = onFoodSelected  )
        }
    }
}

@Composable
fun FoodListItem(food: Food, onFoodSelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onFoodSelected(food.id) },
        elevation = 4.dp,
        backgroundColor = VeryDarkGray
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = food.categoria,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
            Text(
                text = "${food.nutrientes.firstOrNull()?.energia?.toInt()?: 0} kcal",
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}


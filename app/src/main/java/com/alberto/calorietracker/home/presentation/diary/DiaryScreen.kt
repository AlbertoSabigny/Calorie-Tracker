package com.alberto.calorietracker.home.presentation.diary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alberto.calorietracker.home.presentation.diary.components.MacroNutrientCard
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    onNavigateToSearch: (LocalDate) -> Unit,
    viewModel: DiaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.selectedDate.
        atStartOfDay(ZoneOffset.UTC).
        toInstant().
        toEpochMilli()
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.onEvent(DiaryEvent.NavigateToPreviousDay) }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Día anterior",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = state.topBarText, color = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { viewModel.onEvent(DiaryEvent.NavigateToNextDay) }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Día siguiente",
                                tint = Color.White
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToSearch(state.selectedDate) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar comida",
                    tint = Color.White
                )
            }
        },
        containerColor = Color.Black
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(3.dp)
        ) {
            item {
                MacroNutrientCard(
                    carbs = Pair(35, 70),
                    proteins = Pair(50, 100),
                    fats = Pair(20, 50),
                    calories = Pair(1350, 2300),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item { MealSection(title = "Desayuno", onNavigateToSearch = { onNavigateToSearch(state.selectedDate) }) }
            item { MealSection(title = "Comida", onNavigateToSearch = { onNavigateToSearch(state.selectedDate) }) }
            item { MealSection(title = "Cena", onNavigateToSearch = { onNavigateToSearch(state.selectedDate) }) }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let {
                            val newSelectedDate = Instant.ofEpochMilli(it)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                            viewModel.onEvent(DiaryEvent.UpdateSelectedDate(newSelectedDate))
                        }
                    }
                ) {
                    Text("OK", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = Color.White)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = Color.Black,
                titleContentColor = Color.White,
                headlineContentColor = Color.White,
                weekdayContentColor = Color.White,
                subheadContentColor = Color.White,
                yearContentColor = Color.White,
                currentYearContentColor = Color.White,
                selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                selectedYearContentColor = Color.White,
                dayContentColor = Color.White,
                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                selectedDayContentColor = Color.White,
                todayContentColor = MaterialTheme.colorScheme.primary,
                todayDateBorderColor = MaterialTheme.colorScheme.primary
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    headlineContentColor = Color.White,
                    weekdayContentColor = Color.White,
                    subheadContentColor = Color.White,
                    yearContentColor = Color.White,
                    currentYearContentColor = Color.White,
                    selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                    selectedYearContentColor = Color.White,
                    dayContentColor = Color.White,
                    selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                    selectedDayContentColor = Color.White,
                    todayContentColor = MaterialTheme.colorScheme.primary,
                    todayDateBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}


@Composable
fun MealSection(title: String, onNavigateToSearch: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            IconButton(onClick = { onNavigateToSearch() }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Agregar $title",
                    tint = Color.White
                )
            }
        }
        // Aquí puedes agregar los ítems de cada comida
        // Por ejemplo:
        MealItem("Huevos revueltos", "200 kcal")
        MealItem("Tostada integral", "80 kcal")
    }
}

@Composable
fun MealItem(name: String, calories: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Text(
            text = calories,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

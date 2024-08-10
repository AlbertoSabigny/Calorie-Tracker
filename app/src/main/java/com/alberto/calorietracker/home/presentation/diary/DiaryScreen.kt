package com.alberto.calorietracker.home.presentation.diary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alberto.calorietracker.home.presentation.diary.components.MacroNutrientCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen (
    onNavigateToSearch: () -> Unit
   // state: DatePickerState = rememberDatePickerState()
) {
    var showDate by remember {
        mutableStateOf(false)
    }
    val state = rememberDatePickerState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black,
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Hoy",
                color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { showDate = true }){
                        Icon(imageVector = Icons.Default.DateRange,
                            contentDescription = "calendar",
                            tint = Color.White)
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black // Cambiado el color de fondo de la barra superior
                ))
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {onNavigateToSearch()},
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Habit",
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    ) {paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
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

            item {
                MealSection(title = "Desayuno")
            }

            item {
                MealSection(title = "Comida")
            }

            item {
                MealSection(title = "Cena")
            }
        }


        if(showDate){
            DatePickerDialog(
                onDismissRequest = { showDate = false },
                confirmButton = {
                    Button(onClick = { showDate = false} ){
                        Text(text = "Confirmar")
                    }
                }
            ) {
                DatePicker(state = state)
            }
        }


    }
}


@Composable
fun MealSection(title: String) {
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
            IconButton(onClick = { /* Acción para agregar */ }) {
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

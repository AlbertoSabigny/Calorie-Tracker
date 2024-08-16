package com.alberto.calorietracker.home.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alberto.calorietracker.home.domain.model.Food
import com.alberto.calorietracker.home.domain.model.Nutrients
import com.alberto.calorietracker.ui.theme.OrangeMedium
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    foodId: String,
    date: Long,
    viewModel: FoodDetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onNavigateToDiary: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(foodId) {
        viewModel.onEvent(FoodDetailEvent.LoadFood(foodId))
    }
    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            onNavigateToDiary()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.food?.nombre ?: "Detalles del Alimento", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingIndicator()
            uiState.error != null -> ErrorMessage(uiState.error)
            uiState.food != null -> FoodDetailContent(
                food = uiState.food!!,
                customAmount = uiState.customAmount,
                selectedMealType = uiState.selectedMealType,
                onCustomAmountChange = { viewModel.onEvent(FoodDetailEvent.UpdateCustomAmount(it)) },
                onMealTypeChange = { viewModel.onEvent(FoodDetailEvent.UpdateMealType(it)) },
                onSave = { viewModel.onEvent(FoodDetailEvent.SaveFoodConsumption(date)) },
                saveSuccess = uiState.saveSuccess,
                modifier = Modifier.padding(padding),
                date = date,
                calories = uiState.calories,
                proteins = uiState.proteins,
                fats = uiState.fats,
                carbohydrates = uiState.carbohydrates
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailContent(
    food: Food,
    customAmount: String,
    selectedMealType: MealType,
    onCustomAmountChange: (String) -> Unit,
    onMealTypeChange: (MealType) -> Unit,
    onSave: () -> Unit,
    saveSuccess: Boolean,
    modifier: Modifier = Modifier,
    date: Long,
    calories: Double,
    proteins: Double,
    fats: Double,
    carbohydrates: Double
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Información básica del alimento
        Text(
            text = food.nombre,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = food.categoria,
            style = MaterialTheme.typography.titleMedium,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar la fecha seleccionada
        val dateLocal = LocalDate.ofEpochDay(date).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        Text(
            text = "Fecha: ${dateLocal}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para ingresar cantidad personalizada
        OutlinedTextField(
            value = customAmount,
            onValueChange = onCustomAmountChange,
            label = { Text("Cantidad consumida (${food.unidadBase})", color = Color.White) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Menú desplegable para seleccionar el tipo de comida
        MealTypeDropdown(
            selectedMealType = selectedMealType,
            onMealTypeChange = onMealTypeChange
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Información nutricional
        Text(
            text = "Información Nutricional",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        NutrientInfoCard(calories, proteins, fats, carbohydrates)
        Spacer(modifier = Modifier.height(16.dp))

        // Botón para guardar
        Button(
            onClick = onSave,
            colors = ButtonDefaults.buttonColors(containerColor = OrangeMedium),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Alimento", color = Color.White)
        }

        // Mensaje de éxito
        if (saveSuccess) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "¡Guardado con éxito!",
                color = Color.Green,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealTypeDropdown(
    selectedMealType: MealType,
    onMealTypeChange: (MealType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val mealTypes = MealType.entries.toTypedArray()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = when (selectedMealType) {
                MealType.BREAKFAST -> "Desayuno"
                MealType.LUNCH -> "Comida"
                MealType.DINNER -> "Cena"
            },
            onValueChange = { },
            label = { Text("Tipo de comida", color = Color.White) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                //textColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            mealTypes.forEach { mealType ->
                DropdownMenuItem(
                    text = {
                        Text(
                            when (mealType) {
                                MealType.BREAKFAST -> "Desayuno"
                                MealType.LUNCH -> "Comida"
                                MealType.DINNER -> "Cena"
                            }
                        )
                    },
                    onClick = {
                        onMealTypeChange(mealType)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun NutrientInfoCard(calories: Double, proteins: Double, fats: Double, carbohydrates: Double) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            NutrientRow("Energía", "${calories.toInt()} kcal")
            NutrientRow("Proteínas", "${proteins.toInt()} g")
            NutrientRow("Lípidos", "${fats.toInt()} g")
            NutrientRow("Carbohidratos", "${carbohydrates.toInt()} g")
        }
    }
}

@Composable
fun NutrientRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.LightGray)
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}

@Composable
fun ErrorMessage(error: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Error: $error",
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
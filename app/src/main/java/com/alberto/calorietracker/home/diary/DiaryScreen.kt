package com.alberto.calorietracker.home.diary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alberto.calorietracker.home.diary.components.MacroNutrientCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen (
   // state: DatePickerState = rememberDatePickerState()
) {
    var showDate by remember {
        mutableStateOf(false)
    }
    val state = rememberDatePickerState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Hoy") },
                navigationIcon = {
                    IconButton(onClick = { showDate = true }){
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = "calendar")
                    }
                })
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {},
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
                    //.padding(start = 20.dp)
                    .padding(top = 15.dp),
                verticalArrangement = Arrangement.spacedBy(100.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ){
                item{
                    MacroNutrientCard(
                        carbs = Pair(35, 70),
                        proteins = Pair(50, 100),
                        fats = Pair(20, 50),
                        calories = Pair(1350, 2300),
                        modifier = Modifier.fillMaxWidth()
                    )
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




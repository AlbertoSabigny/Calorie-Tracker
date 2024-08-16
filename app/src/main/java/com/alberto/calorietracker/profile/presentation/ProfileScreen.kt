package com.alberto.calorietracker.profile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    logout: () -> Unit) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.bodyLarge) },
                actions = {
                    IconButton(onClick = logout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else if (state.errorMessage != null) {
                Text(text = state.errorMessage!!, color = Color.Red, modifier = Modifier.padding(16.dp))
            } else {
                OutlinedTextField(
                    value = state.editableAge,
                    onValueChange = { viewModel.onEvent(ProfileEvent.OnAgeChanged(it)) },
                    label = { Text("Edad") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.editableHeight,
                    onValueChange = { viewModel.onEvent(ProfileEvent.OnHeightChanged(it)) },
                    label = { Text("Altura") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.editableWeight,
                    onValueChange = { viewModel.onEvent(ProfileEvent.OnWeightChanged(it)) },
                    label = { Text("Peso") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.editableGender,
                    onValueChange = { viewModel.onEvent(ProfileEvent.OnGenderChanged(it)) },
                    label = { Text("Género") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.editableActivityLevel,
                    onValueChange = { viewModel.onEvent(ProfileEvent.OnActivityLevelChanged(it)) },
                    label = { Text("Nivel de Actividad") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.editableTargetWeight,
                    onValueChange = { viewModel.onEvent(ProfileEvent.OnTargetWeightChanged(it)) },
                    label = { Text("Peso Objetivo") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.editableGoalSpeed,
                    onValueChange = { viewModel.onEvent(ProfileEvent.OnGoalSpeedChanged(it)) },
                    label = { Text("Velocidad de Meta") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.onEvent(ProfileEvent.SaveUserProfile) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
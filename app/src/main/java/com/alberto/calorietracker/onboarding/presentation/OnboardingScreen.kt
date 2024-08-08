package com.alberto.calorietracker.onboarding.presentation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.alberto.calorietracker.onboarding.components.ActivityLevelInput
import com.alberto.calorietracker.onboarding.components.DataSummary
import com.alberto.calorietracker.onboarding.components.FitnessOnboardingPager
import com.alberto.calorietracker.onboarding.components.GoalSpeedInput
import com.alberto.calorietracker.onboarding.components.PersonalDataInput
import com.alberto.calorietracker.onboarding.components.WeightGoalInput
import com.alberto.calorietracker.onboarding.components.WelcomePage

@Composable
fun OnboardingScreen (
    viewModel: FitnessOnboardingViewModel = hiltViewModel(),
    onFinish: () -> Unit
){
    val state by viewModel.state.collectAsState()

    val pages = listOf(
        FitnessOnboardingPage(
            title = "Bienvenido a tu viaje fitness",
            subtitle = "Transforma tu vida, un paso a la vez",
            content = {WelcomePage() }  // La imagen de bienvenida se maneja directamente en el pager
        ),
        FitnessOnboardingPage(

            title = "Datos personales",
            subtitle = "Cuéntanos un poco sobre ti",
            content = { PersonalDataInput(state = state, onEvent = viewModel::onEvent) }
        ),
        FitnessOnboardingPage(
            title = "Nivel de Actividad",
            subtitle = "ingresa tu nive",
            content = {ActivityLevelInput(state = state, onEvent = viewModel::onEvent)}
        ),
        FitnessOnboardingPage(
            title = "Tu objetivo",
            subtitle = "Establece tu meta de peso",
            content = { WeightGoalInput(state = state, onEvent = viewModel::onEvent) }
        ),
        FitnessOnboardingPage(
            title = "Plan de acción",
            subtitle = "Elige tu ritmo",
            content = { GoalSpeedInput(state = state, onEvent = viewModel::onEvent) }
        ),
        FitnessOnboardingPage(
            title = "¡Listo para comenzar!",
            subtitle = "Revisa tus datos y empieza tu viaje fitness",
            content = { DataSummary(state = state, onEvent = viewModel::onEvent, onFinish) }
        )
    )

    FitnessOnboardingPager(pages = pages, onFinish = {viewModel.onEvent(FitnessOnboardingEvent.CompleteOnboarding)}, onEvent = viewModel::onEvent)
}









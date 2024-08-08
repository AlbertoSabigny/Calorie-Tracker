package com.alberto.calorietracker.onboarding.domain.usecase

import com.alberto.calorietracker.onboarding.domain.model.ActivityLevel
import javax.inject.Inject

class GetActivityLevelsUseCase @Inject constructor() {
    operator fun invoke(): List<ActivityLevel> = listOf(
        ActivityLevel("Sedentario", "Poco o ningún ejercicio (TMB × 1.2)"),
        ActivityLevel("Ligero", "Ejercicio ligero/deportes 1-3 días a la semana (TMB × 1.375)"),
        ActivityLevel("Moderado", "Ejercicio moderado/deportes 3-5 días a la semana (TMB × 1.55)"),
        ActivityLevel("Intenso", "Ejercicio intenso/deportes 6-7 días a la semana (TMB × 1.725)"),
        ActivityLevel("Muy Intenso", "Ejercicio muy intenso/trabajo físico (TMB × 1.9)")
    )
}
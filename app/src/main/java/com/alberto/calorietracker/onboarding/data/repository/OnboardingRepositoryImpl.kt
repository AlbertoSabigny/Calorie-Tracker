package com.alberto.calorietracker.onboarding.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.alberto.calorietracker.core.domain.model.UserProfile
import com.alberto.calorietracker.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : OnboardingRepository {

    override suspend fun saveUserProfile(userProfile: UserProfile) {
        sharedPreferences.edit().apply {
            putString("age", userProfile.age)
            putString("height", userProfile.height)
            putString("weight", userProfile.weight)
            putString("gender", userProfile.gender)
            putString("activityLevel", userProfile.activityLevel)
            putString("targetWeight", userProfile.targetWeight)
            putString("goalSpeed", userProfile.goalSpeed)
            putFloat("imc", userProfile.imc.toFloat())
            putFloat("pesoIdealMinimo", userProfile.pesoIdealMinimo.toFloat())
            putFloat("pesoIdealMaximo", userProfile.pesoIdealMaximo.toFloat())
            putFloat("caloriasDiariasRecomendadas", userProfile.caloriasDiariasRecomendadas.toFloat())
        }.apply()
        Log.d("UserProfile", "Perfil guardado: $userProfile")
    }
}
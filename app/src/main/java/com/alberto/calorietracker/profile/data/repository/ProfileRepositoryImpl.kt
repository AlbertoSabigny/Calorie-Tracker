package com.alberto.calorietracker.profile.data.repository

import android.content.SharedPreferences
import com.alberto.calorietracker.core.domain.model.UserProfile
import com.alberto.calorietracker.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ProfileRepository{
    override suspend fun getUserProfile(): UserProfile? {
        val age = sharedPreferences.getString("age", "") ?: return null
        val height = sharedPreferences.getString("height", "") ?: return null
        val weight = sharedPreferences.getString("weight", "") ?: return null
        val gender = sharedPreferences.getString("gender", "") ?: return null
        val activityLevel = sharedPreferences.getString("activityLevel", "") ?: return null
        val targetWeight = sharedPreferences.getString("targetWeight", "") ?: return null
        val goalSpeed = sharedPreferences.getString("goalSpeed", "") ?: return null
        val imc = sharedPreferences.getFloat("imc", 0f).toDouble()
        val pesoIdealMinimo = sharedPreferences.getFloat("pesoIdealMinimo", 0f).toDouble()
        val pesoIdealMaximo = sharedPreferences.getFloat("pesoIdealMaximo", 0f).toDouble()
        val caloriasDiariasRecomendadas = sharedPreferences.getFloat("caloriasDiariasRecomendadas", 0f).toDouble()

        return UserProfile(
            age = age,
            height = height,
            weight = weight,
            gender = gender,
            activityLevel = activityLevel,
            targetWeight = targetWeight,
            goalSpeed = goalSpeed,
            imc = imc,
            pesoIdealMinimo = pesoIdealMinimo,
            pesoIdealMaximo = pesoIdealMaximo,
            caloriasDiariasRecomendadas = caloriasDiariasRecomendadas
        )
    }
}
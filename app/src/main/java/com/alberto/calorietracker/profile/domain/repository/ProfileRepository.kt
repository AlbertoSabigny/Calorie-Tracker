package com.alberto.calorietracker.profile.domain.repository

import com.alberto.calorietracker.core.domain.model.UserProfile


interface ProfileRepository {
    suspend fun getUserProfile(): UserProfile?
}
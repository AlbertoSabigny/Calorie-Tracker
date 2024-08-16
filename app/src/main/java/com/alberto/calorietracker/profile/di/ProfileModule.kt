package com.alberto.calorietracker.profile.di

import android.content.SharedPreferences
import com.alberto.calorietracker.profile.data.repository.ProfileRepositoryImpl
import com.alberto.calorietracker.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideOnboardingRepository(sharedPreferences: SharedPreferences): ProfileRepository {
        return ProfileRepositoryImpl(sharedPreferences)
    }
}
package com.alberto.calorietracker.onboarding.di

import android.content.SharedPreferences
import com.alberto.calorietracker.onboarding.data.repository.OnboardingRepositoryImpl
import com.alberto.calorietracker.onboarding.domain.repository.OnboardingRepository
import com.alberto.calorietracker.onboarding.domain.usecase.SaveUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Provides
    @Singleton
    fun provideOnboardingRepository(sharedPreferences: SharedPreferences): OnboardingRepository {
        return OnboardingRepositoryImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSaveUserProfileUseCase(repository: OnboardingRepository): SaveUserProfileUseCase {
        return SaveUserProfileUseCase(repository)
    }

}
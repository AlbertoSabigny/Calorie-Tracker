package com.alberto.calorietracker.home.di

import com.alberto.calorietracker.home.data.repository.FoodRepositoryimpl
import com.alberto.calorietracker.home.data.remote.source.RemoteFoodDataSource
import com.alberto.calorietracker.home.domain.repository.FoodRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideRemoteAlimentoDataSource(firestore: FirebaseFirestore): RemoteFoodDataSource {
        return RemoteFoodDataSource(firestore)
    }

    @Provides
    @Singleton
    fun provideAlimentoRepository(
        remoteDataSource: RemoteFoodDataSource
    ): FoodRepository {
        return FoodRepositoryimpl(remoteDataSource)
    }
}
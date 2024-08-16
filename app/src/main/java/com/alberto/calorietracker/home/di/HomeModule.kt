package com.alberto.calorietracker.home.di

import android.content.Context
import androidx.room.Room
import com.alberto.calorietracker.home.data.local.dao.FoodDao
import com.alberto.calorietracker.home.data.local.database.FoodDatabase
import com.alberto.calorietracker.home.data.repository.FoodRepositoryimpl
import com.alberto.calorietracker.home.data.remote.source.RemoteFoodDataSource
import com.alberto.calorietracker.home.domain.repository.FoodRepository
import com.alberto.calorietracker.home.domain.usecase.SaveFoodConsumptionUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        remoteDataSource: RemoteFoodDataSource,
        foodDao: FoodDao
    ): FoodRepository {
        return FoodRepositoryimpl(remoteDataSource, foodDao)
    }

    @Provides
    @Singleton
    fun provideFoodDatabase(@ApplicationContext context: Context): FoodDatabase {
        return Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            "food_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodDao(db: FoodDatabase): FoodDao = db.getFoodDao()


    @Provides
    fun provideSaveFoodConsumptionUseCase(repository: FoodRepository): SaveFoodConsumptionUseCase {
        return SaveFoodConsumptionUseCase(repository)
    }


}
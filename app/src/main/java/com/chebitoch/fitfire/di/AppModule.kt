package com.chebitoch.fitfire.di

import android.content.Context
import androidx.room.Room
import com.chebitoch.fitfire.data.local.WorkoutPlanDao
import com.chebitoch.fitfire.data.local.WorkoutPlanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWorkoutPlanDatabase(
        @ApplicationContext context: Context
    ): WorkoutPlanDatabase {
        return Room.databaseBuilder(
            context,
            WorkoutPlanDatabase::class.java,
            "workout_plan_db"
        ).build()
    }

    @Provides
    fun provideWorkoutPlanDao(database: WorkoutPlanDatabase): WorkoutPlanDao {
        return database.workoutPlanDao()
    }
}
package com.chebitoch.fitfire.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chebitoch.fitfire.model.WorkoutPlan

@Database(entities = [WorkoutPlan::class], version = 1, exportSchema = false)
abstract class FitFireDatabase : RoomDatabase() {

    abstract fun workoutPlanDao(): WorkoutPlanDao

    companion object {
        @Volatile
        private var INSTANCE: FitFireDatabase? = null

        fun getDatabase(context: Context): FitFireDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitFireDatabase::class.java,
                    "fitfire_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
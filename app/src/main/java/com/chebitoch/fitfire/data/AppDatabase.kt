package com.chebitoch.fitfire.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chebitoch.fitfire.model.User // Assuming User is in this package
import com.chebitoch.fitfire.model.UserProfile // Assuming UserProfile is in this package
import com.chebitoch.fitfire.model.WorkoutPlan // Ensure this import exists

@Database(entities = [UserProfile::class, User::class, WorkoutPlan::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao
    abstract fun userDao(): UserDao
    abstract fun workoutPlanDao(): WorkoutPlanDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fitfire_db" // Keep a consistent database name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
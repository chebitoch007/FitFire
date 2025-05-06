package com.chebitoch.fitfire.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chebitoch.fitfire.model.User // Assuming this is your User Room Entity
import com.chebitoch.fitfire.model.UserProfileEntity // Using the correct UserProfile Room Entity
import com.chebitoch.fitfire.model.WorkoutPlan // Assuming this is your WorkoutPlan Room Entity

@Database(
    entities = [UserProfileEntity::class, User::class, WorkoutPlan::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao
    abstract fun userDao(): UserDao // Ensure this DAO exists
    abstract fun workoutPlanDao(): WorkoutPlanDao // Ensure this DAO exists

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fitfire_db" // Using a consistent database name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
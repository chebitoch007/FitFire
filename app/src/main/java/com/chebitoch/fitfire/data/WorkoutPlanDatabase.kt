package com.chebitoch.fitfire.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chebitoch.fitfire.data.model.WorkoutPlan
import com.chebitoch.fitfire.model.WorkoutPlan

@Database(
    entities = [WorkoutPlan::class],
    version = 1,
    exportSchema = false
)
abstract class WorkoutPlanDatabase : RoomDatabase() {
    abstract fun workoutPlanDao(): WorkoutPlanDao
}
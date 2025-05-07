package com.chebitoch.fitfire.data

import androidx.room.*
import com.chebitoch.fitfire.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanDao {
    @Query("SELECT * FROM workout_plans")
    fun getAllWorkoutPlans(): Flow<List<WorkoutPlan>>

    @Query("SELECT * FROM workout_plans WHERE id = :id")
    suspend fun getWorkoutPlanById(id: Int): WorkoutPlan?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkoutPlan(workoutPlan: WorkoutPlan)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkoutPlans(workoutPlans: List<WorkoutPlan>)

    @Update
    suspend fun updateWorkoutPlan(workoutPlan: WorkoutPlan)

    @Query("DELETE FROM workout_plans WHERE id = :id")
    suspend fun deleteWorkoutPlanById(id: Int)

    @Delete
    suspend fun deleteWorkoutPlan(workoutPlan: WorkoutPlan)
}
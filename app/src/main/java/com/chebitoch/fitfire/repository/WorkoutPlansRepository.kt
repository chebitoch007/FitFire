package com.chebitoch.fitfire.repository

import com.chebitoch.fitfire.data.WorkoutPlanDao
import com.chebitoch.fitfire.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow

class WorkoutPlanRepository(private val workoutPlanDao: WorkoutPlanDao) {

    val allWorkoutPlans: Flow<List<WorkoutPlan>> = workoutPlanDao.getAllWorkoutPlans()

    suspend fun getWorkoutPlanById(id: Int): WorkoutPlan? {
        return workoutPlanDao.getWorkoutPlanById(id)
    }

    suspend fun insertWorkoutPlan(workoutPlan: WorkoutPlan) {
        workoutPlanDao.insertWorkoutPlan(workoutPlan)
    }

    suspend fun insertWorkoutPlans(workoutPlans: List<WorkoutPlan>) {
        workoutPlanDao.insertWorkoutPlans(workoutPlans)
    }

    suspend fun updateWorkoutPlan(workoutPlan: WorkoutPlan) {
        workoutPlanDao.updateWorkoutPlan(workoutPlan)
    }

    suspend fun deleteWorkoutPlan(workoutPlan: WorkoutPlan) {
        workoutPlanDao.deleteWorkoutPlan(workoutPlan)
    }

    suspend fun deleteWorkoutPlanById(id: Int) {
        workoutPlanDao.deleteWorkoutPlanById(id)
    }
}
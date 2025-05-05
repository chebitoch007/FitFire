package com.chebitoch.fitfire.repository

import com.chebitoch.fitfire.R
import com.chebitoch.fitfire.data.WorkoutPlanDao
import com.chebitoch.fitfire.model.WorkoutPlan


class WorkoutPlanRepository @Inject constructor(
    private val dao: WorkoutPlanDao
) {
    fun getAllPlans() = dao.getAllPlans()

    suspend fun getPlanById(id: Int) = dao.getPlanById(id)

    suspend fun insertPlan(plan: WorkoutPlan) = dao.insertPlan(plan)

    suspend fun insertSamplePlans() {
        val samplePlans = listOf(
            WorkoutPlan(name = "Beginner Blast", duration = "4 Weeks", level = "Beginner", target = "Full Body", imageResId = R.drawable.full_body_burn),
            WorkoutPlan(name = "Strength Master", duration = "6 Weeks", level = "Intermediate", target = "Strength", imageResId = R.drawable.upper_body_strength)
        )
        dao.insertAll(samplePlans)
    }

    suspend fun deletePlan(plan: WorkoutPlan) = dao.deletePlan(plan)
}
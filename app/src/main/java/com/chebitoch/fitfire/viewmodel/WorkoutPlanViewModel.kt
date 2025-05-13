package com.chebitoch.fitfire.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chebitoch.fitfire.R
import com.chebitoch.fitfire.data.AppDatabase
import com.chebitoch.fitfire.repository.WorkoutPlanRepository
import com.chebitoch.fitfire.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WorkoutPlanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorkoutPlanRepository
    val allPlans: Flow<List<WorkoutPlan>>

    init {
        val workoutPlanDao = AppDatabase.getDatabase(application).workoutPlanDao()
        repository = WorkoutPlanRepository(workoutPlanDao)
        allPlans = repository.allWorkoutPlans
    }

    fun insertWorkoutPlan(workoutPlan: WorkoutPlan) = viewModelScope.launch {
        repository.insertWorkoutPlan(workoutPlan)
    }

    fun insertSamplePlans() = viewModelScope.launch {
        val samplePlans = listOf(
            WorkoutPlan(name = "Full Body Strength", duration = "45 mins", level = "Intermediate", target = "Overall Strength", imageResId = R.drawable.full_body_burn),
            WorkoutPlan(name = "Cardio Endurance", duration = "30 mins", level = "Beginner", target = "Cardiovascular Health", imageResId = R.drawable.cardio_endurance),
            WorkoutPlan(name = "Leg Day Plans", duration = "60 mins", level = "Advanced", target = "Lower Body Strength", imageResId = R.drawable.leg_day_blast),
            WorkoutPlan(name = "Back Builder", duration = "70 mins", level = "Intermediate", target = "Back Muscle Build", imageResId = R.drawable.back_builder),
            WorkoutPlan(name = "Core Crusher", duration = "100 mins", level = "Advanced", target = "Overall Strength", imageResId = R.drawable.core_crusher),
            WorkoutPlan(name = "Arm Sculpt", duration = "30 mins", level = "Beginner", target = "Arm Strength", imageResId = R.drawable.arm_sculpt),
            WorkoutPlan(name = "Quick Hitt", duration = "30 mins", level = "Intermediate", target = "Body Warmup", imageResId = R.drawable.quick_hitt),
            // Add more sample plans
        )
        repository.insertWorkoutPlans(samplePlans)
    }

    fun updateWorkoutPlan(workoutPlan: WorkoutPlan) = viewModelScope.launch {
        repository.updateWorkoutPlan(workoutPlan)
    }

    fun deleteWorkoutPlan(workoutPlan: WorkoutPlan) = viewModelScope.launch {
        repository.deleteWorkoutPlan(workoutPlan)
    }

    fun deleteWorkoutPlanById(planId: Int) = viewModelScope.launch {
        repository.deleteWorkoutPlanById(planId)
    }

    class WorkoutPlanViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WorkoutPlanViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WorkoutPlanViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
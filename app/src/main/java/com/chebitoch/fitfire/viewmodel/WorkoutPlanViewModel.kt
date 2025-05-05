package com.chebitoch.fitfire.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.chebitoch.fitfire.R
import com.chebitoch.fitfire.data.FitFireDatabase
import com.chebitoch.fitfire.repository.WorkoutPlanRepository
import com.chebitoch.fitfire.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WorkoutPlanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorkoutPlanRepository
    val allPlans: Flow<List<WorkoutPlan>>

    init {
        val workoutPlanDao = FitFireDatabase.getDatabase(application).workoutPlanDao()
        repository = WorkoutPlanRepository(workoutPlanDao)
        allPlans = repository.allWorkoutPlans
    }

    fun insertWorkoutPlan(workoutPlan: WorkoutPlan) = viewModelScope.launch {
        repository.insertWorkoutPlan(workoutPlan)
    }

    fun insertSamplePlans() = viewModelScope.launch {
        val samplePlans = listOf(
            WorkoutPlan(name = "Full Body Strength", duration = "45 mins", level = "Intermediate", target = "Overall Strength", imageResId = R.drawable.ic_launcher_foreground),
            WorkoutPlan(name = "Cardio Blast", duration = "30 mins", level = "Beginner", target = "Cardiovascular Health", imageResId = R.drawable.ic_launcher_foreground),
            WorkoutPlan(name = "Leg Day Focus", duration = "60 mins", level = "Advanced", target = "Lower Body Strength", imageResId = R.drawable.ic_launcher_foreground)
            // Add more sample plans
        )
        repository.insertWorkoutPlans(samplePlans)
    }

    // You can add other functions like update and delete if needed

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
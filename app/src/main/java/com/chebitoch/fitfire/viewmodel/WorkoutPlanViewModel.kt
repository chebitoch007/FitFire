package com.chebitoch.fitfire.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chebitoch.fitfire.data.model.WorkoutPlan
import com.chebitoch.fitfire.data.repository.WorkoutPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutPlanViewModel @Inject constructor(
    private val repository: WorkoutPlanRepository
) : ViewModel() {

    private val _allPlans = MutableStateFlow<List<WorkoutPlan>>(emptyList())
    val allPlans: StateFlow<List<WorkoutPlan>> = _allPlans.asStateFlow()

    private val _selectedPlan = MutableStateFlow<WorkoutPlan?>(null)
    val selectedPlan: StateFlow<WorkoutPlan?> = _selectedPlan.asStateFlow()

    init {
        loadWorkoutPlans()
    }

    private fun loadWorkoutPlans() {
        viewModelScope.launch {
            repository.getAllPlans().collect { plans ->
                _allPlans.value = plans
            }
        }
    }

    fun getWorkoutPlanById(id: Int) {
        viewModelScope.launch {
            _selectedPlan.value = repository.getPlanById(id)
        }
    }

    fun insertSamplePlans() {
        viewModelScope.launch {
            repository.insertSamplePlans()
        }
    }

    fun insertWorkoutPlan(plan: WorkoutPlan) {
        viewModelScope.launch {
            repository.insertPlan(plan)
        }
    }

    fun deleteWorkoutPlan(plan: WorkoutPlan) {
        viewModelScope.launch {
            repository.deletePlan(plan)
        }
    }
}

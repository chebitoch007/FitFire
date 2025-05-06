package com.chebitoch.fitfire.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chebitoch.fitfire.data.UserProfileDao
import com.chebitoch.fitfire.model.UserProfileEntity // Import the Room Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class UserProfile(
    val name: String = "",
    val age: Int = 0,
    val height: Int = 0,
    val weight: Double = 0.0,
    val goal: String = ""
)

class ProfileViewModel(
    private val userProfileDao: UserProfileDao
) : ViewModel() {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            val existingProfile = userProfileDao.getUserProfile() // Use getUserProfile() for one-time fetch
            existingProfile?.let {
                _userProfile.value = it.toViewModelUserProfile() // Map from Entity to ViewModel
            }
        }
    }

    fun saveUserProfile(userProfile: UserProfile) { // Take ViewModel's UserProfile
        viewModelScope.launch {
            userProfileDao.insertOrUpdate(userProfile.toUserProfileEntity()) // Map to Entity before saving
            // If you're observing getUserProfileFlow() in your UI, you don't need to call fetchUserProfile() here
            // fetchUserProfile() // Remove or comment out this line if using Flow in UI
        }
    }
}

// Extension functions for mapping between entities
private fun UserProfileEntity.toViewModelUserProfile(): UserProfile {
    return UserProfile(
        name = this.name,
        age = this.age,
        height = this.height,
        weight = this.weight,
        goal = this.goal
    )
}

private fun UserProfile.toUserProfileEntity(): UserProfileEntity {
    return UserProfileEntity(
        name = this.name,
        age = this.age,
        height = this.height,
        weight = this.weight,
        goal = this.goal
    )
}
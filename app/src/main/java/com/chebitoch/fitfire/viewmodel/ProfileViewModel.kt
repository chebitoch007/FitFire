package com.chebitoch.fitfire.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chebitoch.fitfire.data.UserProfileDao
import com.chebitoch.fitfire.model.UserProfileEntity // Import the Room Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserProfile(
    val name: String = "",
    val age: Int = 0,
    val height: Int = 0,
    val weight: Double = 0.0,
    val goal: String = "",
    val id: Any? = null // Make id nullable and use Any to match potential Room types
)

class ProfileViewModel(
    private val userProfileDao: UserProfileDao
) : ViewModel() {

    private val _userProfile = MutableStateFlow(UserProfile()) // Initialize with default values
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

    class Factory(private val application: Application) : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                val database = com.chebitoch.fitfire.data.AppDatabase.getDatabase(application)
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(database.userProfileDao()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
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
        goal = this.goal,
        id = this.id
    )
}

private fun UserProfile.toUserProfileEntity(): UserProfileEntity {
    return UserProfileEntity(
        id = this.id as? Int ?: 0, // Handle potential null or incorrect type
        name = this.name,
        age = this.age,
        height = this.height,
        weight = this.weight,
        goal = this.goal
    )
}
package com.chebitoch.fitfire.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel {
    data class UserProfile(
        val name: String,
        val goal: String,
        val age: Int,
        val height: Int, // in cm
        val weight: Int  // in kg
    )

    class ProfileViewModel : ViewModel() {
        private val _userProfile = MutableStateFlow(
            UserProfile("John Doe", "Lose Weight", 25, 170, 68)
        )
        val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

        // Simulate update
        fun updateProfile(profile: UserProfile) {
            _userProfile.value = profile
        }
    }

}
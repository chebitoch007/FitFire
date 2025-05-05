package com.chebitoch.fitfire.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.chebitoch.fitfire.data.AppDatabase
import com.chebitoch.fitfire.data.UserProfile as RoomUserProfile // Alias
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class UserProfile( // ViewModel's UI state
    val name: String = "",
    val age: Int = 0,
    val height: Int = 0,
    val weight: Double = 0.0,
    val goal: String = ""
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userProfileDao()
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            userDao.getUserProfile().collect { dbProfile ->
                _userProfile.value = dbProfile?.let {
                    UserProfile(
                        name = it.name,
                        age = it.age,
                        height = it.height,
                        weight = it.weight,
                        goal = it.goal
                    )
                } ?: UserProfile()
            }
        }
    }

    fun saveUserProfile(profile: com.chebitoch.fitfire.model.UserProfile) {
        viewModelScope.launch {
            val existingProfile = userDao.getUserProfile().firstOrNull()
            val roomUserProfile = RoomUserProfile(
                id = existingProfile?.id ?: 0, // If exists, keep ID, else new
                name = profile.name,
                age = profile.age,
                height = profile.height,
                weight = profile.weight,
                goal = profile.goal
            )
            if (existingProfile == null) {
                userDao.insert(roomUserProfile)
            } else {
                userDao.update(roomUserProfile)
            }
        }
    }
}
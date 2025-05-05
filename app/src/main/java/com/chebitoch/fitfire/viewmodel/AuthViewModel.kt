package com.chebitoch.fitfire.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.chebitoch.fitfire.data.AppDatabase
import com.chebitoch.fitfire.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    /**
     * Attempts to log in the user by checking credentials against the database.
     * Invokes the callback with `true` if login succeeds, otherwise `false`.
     */
    fun loginUser(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getUserByCredentials(username, password)
            callback(user != null)
        }
    }

    /**
     * Attempts to register a new user. Checks if username is already taken.
     * Invokes the callback with `true` if registration succeeds, otherwise `false`.
     */
    fun registerUser(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                callback(false) // Username already exists
            } else {
                val newUser = User(username = username, password = password)
                userDao.insertUser(newUser)
                callback(true)
            }
        }
    }

    companion object {
        fun Factory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return AuthViewModel(application) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
package com.chebitoch.fitfire.repository

import com.chebitoch.fitfire.data.UserDao
import com.chebitoch.fitfire.model.User

open class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserByCredentials(username: String, password: String): User? {
        return userDao.getUserByCredentials(username, password)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
}
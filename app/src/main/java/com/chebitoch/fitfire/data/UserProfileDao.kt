package com.chebitoch.fitfire.data

import androidx.room.*
import com.chebitoch.fitfire.model.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(userProfile: UserProfileEntity)

    @Query("SELECT * FROM user_profile LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile LIMIT 1")
    suspend fun getUserProfile(): UserProfileEntity?

    @Insert
    suspend fun insert(userProfile: UserProfileEntity)

    @Update
    suspend fun update(userProfile: UserProfileEntity)

    @Delete
    suspend fun delete(userProfile: UserProfileEntity)

    @Query("SELECT * FROM user_profile WHERE id = :id")
    suspend fun getUserProfileById(id: Int): UserProfileEntity?

    // You might want to add more queries based on your application's needs
    // For example, if you need to fetch all profiles (though typically you'd only have one for a user)
    // @Query("SELECT * FROM user_profile")
    // fun getAllUserProfiles(): Flow<List<UserProfileEntity>>
}
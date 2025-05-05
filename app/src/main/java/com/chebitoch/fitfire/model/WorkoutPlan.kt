package com.chebitoch.fitfire.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_plans")
data class WorkoutPlan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val duration: String,
    val level: String,
    val target: String,
    val imageResId: Int
)
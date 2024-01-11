package com.example.login.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.Nullable

@Entity
data class User (
    val name: String,
    val email: String,
    val pass: Int,
    val pfp: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
}
package com.example.login.data

import androidx.room.*

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val pass: String,
    val pfp: String
) {
}
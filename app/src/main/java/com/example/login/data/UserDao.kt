package com.example.login.data

import androidx.room.*

interface UserDao {
    @Query("SELECT * FROM User WHERE email = :email AND pass = :pass LIMIT 1")
    fun get(email: String, pass: String): User?
}
package com.example.login.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun all(): LiveData<List<User>>
    @Query("SELECT * FROM User WHERE email = :email AND pass = :pass LIMIT 1")
    fun get(email: String, pass: String): User?
    @Query("SELECT email FROM User WHERE email = :email")
    fun emailExist(email: String): String?
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(user: User)
    @Update
    fun update(user: User)
    @Delete
    fun delete(user: User)
}
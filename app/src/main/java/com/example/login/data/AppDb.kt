package com.example.login.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [], version = 3)
abstract class AppDb : RoomDatabase() {


    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null

        operator fun get(context: Context) = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                AppDb::class.java,
                "tasks"
            ).build().also {
                INSTANCE = it
            }
        }
    }
}
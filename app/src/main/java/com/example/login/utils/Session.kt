package com.example.login.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.login.LoginActivity
import com.example.login.data.AppDb
import com.example.login.data.User

class Session (private val context: Context){
    private val db = AppDb[context]
    private val sessionTimeoutDuration = 10 * 60 * 1000
    private var lastInteractionTime: Long = System.currentTimeMillis()
    private val prefKey = "session"
    private val session = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
    private val edit = session.edit()
    fun onUserInteraction() { lastInteractionTime = System.currentTimeMillis() }
    fun checkSessionTimeout(exec: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastInteractionTime > sessionTimeoutDuration)
            logout()
        else exec()
    }
    fun logout () {
        val intent = Intent(context, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(context, intent, Bundle())
    }

    // ZMIENNE SESJI
    private fun put (f: () -> Unit) { f(); edit.apply() }
    var username: String
        get() = session.getString("userId", "")!!
        set(value) = put { edit.putString("userId", value) }

    var email: String
        get() = session.getString("userEmail", "")!!
        set(value) = put { edit.putString("userEmail", value) }

    var pfp: String?
        get() = session.getString("userPfp", "")
        set(value) = put { edit.putString("userPfp", value) }

    var user: User
        get() = session.getInt("id", -1).let { db.userDao().getById(it)!! }
        set(value) = put { edit.putInt("id", value.id) }
}
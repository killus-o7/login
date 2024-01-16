package com.example.login.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.login.LoginActivity
import com.example.login.data.AppDb
import java.util.Calendar

class Session (private val context: Context){
    private val sessionTimeoutDuration = 10 * 60 * 1000
    private var lastInteractionTime: Long = System.currentTimeMillis()
    private val prefKey = "session"
    private val session = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
    private val edit = session.edit()
    fun onUserInteraction() { lastInteractionTime = System.currentTimeMillis() }
    fun checkSessionTimeout() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastInteractionTime > sessionTimeoutDuration)
            logout()
    }
    fun logout () {
        active = false
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
        get() = session.getString("userPfp", "")!!
        set(value) = put { edit.putString("userPfp", value) }

    var active: Boolean
        get() = session.getBoolean("active", false)
        set(value) = put { edit.putBoolean("active", value) }
}
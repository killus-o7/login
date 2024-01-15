package com.example.login.utils

import android.content.Context
import com.example.login.data.AppDb
import java.util.Calendar

class Session (context: Context){
    private val prefKey = "session"
    private val session = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
    private val edit = session.edit()
    fun logout () { active = false }
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
    var timer: Calendar
        get() = Calendar.getInstance().apply { timeInMillis = session.getLong("timer", -1) }
        set(value) = put { edit.putLong("timer", value.timeInMillis ) }

    var active: Boolean
        get() = session.getBoolean("active", false)
        set(value) = put { edit.putBoolean("active", value) }
}
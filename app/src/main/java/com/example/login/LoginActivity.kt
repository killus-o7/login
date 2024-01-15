package com.example.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.login.data.AppDb
import com.example.login.data.User
import com.example.login.databinding.ActivityLoginBinding
import com.example.login.ui.LoginAdapter
import com.example.login.utils.BaseActivity
import com.example.login.utils.Session


class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override val b by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val adapter by lazy { LoginAdapter { user -> onProfileClick(user) }}

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b.recycler.adapter = adapter
        db.userDao().all().observe(this) { adapter.data = it; adapter.notifyDataSetChanged() }

        b.registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (session.active)
            startMainActivity()
    }

    private fun onProfileClick(user: User) {
        // Zrób dialog by się zalogować

        session.active = true
        session.username = user.name
        session.email = user.email
        session.pfp

        startMainActivity()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.login.data.AppDb
import com.example.login.databinding.ActivityLoginBinding
import com.example.login.ui.LoginAdapter


class LoginActivity : AppCompatActivity() {
    private val b by lazy { ActivityLoginBinding.inflate(layoutInflater)}
    private val db by lazy { AppDb[this] }
    private val adapter by lazy { LoginAdapter() }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.recycler.adapter = adapter
        db.userDao().all().observe(this) { adapter.data = it; adapter.notifyDataSetChanged() }

        b.registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
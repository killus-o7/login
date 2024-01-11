package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.login.databinding.ActivityLoginBinding
import com.example.login.ui.LoginAdapter

class LoginActivity : AppCompatActivity() {
    private val b by lazy { ActivityLoginBinding.inflate(layoutInflater)}
    private val adapter by lazy { LoginAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.recycler.adapter = adapter
    }
}
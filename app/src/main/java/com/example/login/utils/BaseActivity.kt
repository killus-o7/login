package com.example.login.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.login.data.AppDb

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    abstract val b: T
    protected val db by lazy { AppDb[this] }
    protected val session by lazy { Session(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
    }
}



package com.example.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.login.data.User
import com.example.login.databinding.ActivityLoginBinding
import com.example.login.ui.LoginAdapter
import com.example.login.utils.BaseActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


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
        session.checkSessionTimeout {
            startMainActivity()
        }
    }

    private fun onProfileClick(user: User) {
        MaterialAlertDialogBuilder(this).apply {
            val et =
                TextInputEditText(context).apply {
                    setText(pfp)
                }

            val layout =
                TextInputLayout(context).apply {
                    val margin = resources.getDimensionPixelSize(R.dimen.dialogInputMargin)
                    setPadding(margin, 0, margin, 0)
                    addView(et)
                }

            setTitle(R.string.pfpDialogTitle)
            setView(layout)
            setPositiveButton(R.string.pfpDialogPositive) { _, _ ->
                pfp = et.text.toString()
            }
            setNegativeButton(R.string.pfpDialogNeutral, null)
        }.show()

        session.active = true
        session.user = user
        session.username = user.name
        session.email = user.email
        session.pfp = user.pfp

        startMainActivity()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
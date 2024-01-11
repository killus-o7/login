package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.databinding.ActivityRegisterBinding
import com.example.login.ui.ValidatedTextInputLayout.Case
import java.util.regex.Pattern
import android.util.Patterns.EMAIL_ADDRESS
import com.example.login.data.AppDb
import com.example.login.data.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val b by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val db by lazy { AppDb[this] }
    private val inputs by lazy { listOf(b.name, b.email, b.pass, b.repeatPass) }
    private var currentPfp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.button.setOnClickListener { submit() }

        /* WALIDACJA */
        val passPattern: Pattern = Pattern
            .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\\\S+\$).{8,}\$")
        fun getPass() = b.pass.editText?.text.toString()

        b.name.cases = listOf(
            Case(R.string.formNameTooShort) { it.length >= 3 },
            Case(R.string.formNameTooLong) { it.length <= 20 }
        )

        b.email.cases = listOf(
            Case(R.string.formEmailInvalid) { EMAIL_ADDRESS.matcher(it).matches() }
        )

        b.pass.cases = listOf(
            Case(R.string.formPassTooShort) { it.length >= 8 },
            Case(R.string.formPassTooLong) {it.length <= 24},
            //Case(R.string.formPassRegexError) { passPattern.matcher(it).matches() },
        )

        b.repeatPass.cases = listOf(
            Case(R.string.formPassNotMatch) {
                it == getPass()
            }
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun submit(){
        if (inputs.count { it.valid() } != inputs.size) return

        User(
            b.name.editText?.text.toString(),
            b.email.editText?.text.toString(),
            b.pass.editText?.text.toString().hashCode(),
            null
        ).let { GlobalScope.launch { db.userDao().insert(it) }}

        closeActivity()
    }
    private fun closeActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
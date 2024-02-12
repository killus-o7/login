package com.example.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import com.example.login.data.User
import com.example.login.databinding.ActivityRegisterBinding
import com.example.login.ui.ValidatedInputLayout
import com.example.login.utils.BaseActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    override val b by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val inputs by lazy { listOf(b.name, b.email, b.pass, b.repeatPass) }
    private var user: User = User("", "", 0, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b.button.setOnClickListener { submit() }
        b.image.setOnClickListener {
            user.changePfp(this, b.image)
        }
        applyCases()
    }



    private fun submit() = ValidatedInputLayout.validate(inputs) { onSubmit() }


    @OptIn(DelicateCoroutinesApi::class)
    private fun onSubmit() {
        User(
            b.name.editText?.text.toString(),
            b.email.editText?.text.toString(),
            b.pass.editText?.text.toString().hashCode(),
            user.pfp
        ).let { GlobalScope.launch { db.userDao().insert(it) }}
        closeActivity()
    }

    private fun applyCases(){
        fun emailMatches (s: String) = Patterns.EMAIL_ADDRESS.matcher(s).matches()
        b.apply {
            name.cases = listOf(
                R.string.formNameTooShort to { this.length >= 3 },
                R.string.formNameTooLong to { this.length <= 20 }
            )
            email.cases = listOf(
                R.string.formEmailInvalid to { emailMatches(this) },
                R.string.formEmailExists to { db.userDao().emailExist(this) == null }
            )
            pass.cases = listOf(
                R.string.formPassTooShort to { this.length >= 8 },
                R.string.formPassTooLong to { this.length <= 24 },
            )
            repeatPass.cases = listOf(
                R.string.formPassNotMatch to { this == repeatPass.editText?.text.toString() }
            )
        }
    }
    private fun closeActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
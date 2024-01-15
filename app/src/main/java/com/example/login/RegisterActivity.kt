package com.example.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.databinding.ActivityRegisterBinding
import com.example.login.ui.ValidatedTextInputLayout.Case
import java.util.regex.Pattern
import android.util.Patterns.EMAIL_ADDRESS
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.login.data.AppDb
import com.example.login.data.User
import com.example.login.utils.BaseActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    override val b by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val inputs by lazy { listOf(b.name, b.email, b.pass, b.repeatPass) }
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b.button.setOnClickListener { submit() }
        b.image.setOnClickListener { getImage.launch("image/*") }

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
            Case(R.string.formPassTooLong) { it.length <= 24 },
        )

        b.repeatPass.cases = listOf(
            Case(R.string.formPassNotMatch) { it == getPass() }
        )
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        if (it != null)
            Glide.with(this).load(it).into(b.image)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun submit(){
        if (inputs.count { it.valid() } != inputs.size) return

        val imageExt = contentResolver.getType(imageUri!!)?.split("/")?.last() ?: "jpg"
        val imageFile = filesDir.resolve("${UUID.randomUUID()}.$imageExt")
        contentResolver.openInputStream(imageUri!!)?.use { input ->
            imageFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        User(
            b.name.editText?.text.toString(),
            b.email.editText?.text.toString(),
            b.pass.editText?.text.toString().hashCode(),
            imageFile.name
        ).let { GlobalScope.launch { db.userDao().insert(it) }}

        closeActivity()
    }
    private fun closeActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
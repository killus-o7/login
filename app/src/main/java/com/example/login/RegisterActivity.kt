package com.example.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.login.databinding.ActivityRegisterBinding
import java.util.regex.Pattern
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.login.data.User
import com.example.login.ui.ValidatedInputLayout
import com.example.login.utils.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        if (it != null)
            Glide.with(this).load(it).into(b.image)
    }

    private fun submit() {
            ValidatedInputLayout.validate(inputs) { onSubmit() }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun onSubmit() {
        /*        val imageExt = contentResolver.getType(imageUri!!)?.split("/")?.last() ?: "jpg"
        val imageFile = filesDir.resolve("${UUID.randomUUID()}.$imageExt")
        contentResolver.openInputStream(imageUri!!)?.use { input ->
            imageFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }*/

        User(
            b.name.editText?.text.toString(),
            b.email.editText?.text.toString(),
            b.pass.editText?.text.toString().hashCode(),
            null //imageFile.name
        ).let { GlobalScope.launch { db.userDao().insert(it) }}
        closeActivity()
    }
    private fun closeActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
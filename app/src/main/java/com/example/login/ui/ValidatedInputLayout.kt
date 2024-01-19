package com.example.login.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.login.R
import com.example.login.data.AppDb
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class ValidatedInputLayout(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {
    private val db = AppDb[context]
    private var cases: List<Case>
    private var passInput: ValidatedInputLayout? = null
    private var result: Boolean = true
    suspend fun valid(): Boolean {
        reset()
        cases.forEach { check(it) }
        return result
    }
    private fun reset() {
        result = true

        CoroutineScope(Dispatchers.Main).launch {
            this@ValidatedInputLayout.error = null
            Toast.makeText(context, passInput?.editText?.text.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private suspend fun check (case: Case) {
        val viewText = this.editText?.text.toString()
        val isValid = case.condition(viewText)
        result = result && isValid
        CoroutineScope(Dispatchers.Main).launch {
            if (!isValid && this@ValidatedInputLayout.error == null)
                this@ValidatedInputLayout.error = resources.getString(case.text)
        }
    }
    data class Case (
        val text: Int,
        val condition: suspend (it: String) -> Boolean
    )

    private val Cases = listOf (
        listOf(
            Case(R.string.formNameTooShort) { it.length >= 3 },
            Case(R.string.formNameTooLong) { it.length <= 20 }
        ), listOf(
            Case(R.string.formEmailInvalid) { Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            Case(R.string.formEmailExists) { db.userDao().emailExist(it) == null }
        ), listOf(
            Case(R.string.formPassTooShort) { it.length >= 8 },
            Case(R.string.formPassTooLong) { it.length <= 24 },
        ), listOf(
            Case(R.string.formPassNotMatch) { it == passInput?.editText?.text.toString() }
        )
    )
    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ValidatedTextInputLayout,
            0, 0
        ).apply {
            cases = getInteger(R.styleable.ValidatedTextInputLayout_validator, 0)
                .let { Cases[it] }
            passInput = findViewById(getResourceId(
                R.styleable.ValidatedTextInputLayout_passInput,
                -1)
            )
            Log.d("a", passInput?.placeholderText.toString())
            recycle()
        }
    }
    companion object {
        fun validate (
            inputs: List<ValidatedInputLayout>,
            onValid: () -> Unit,
            passInput: ValidatedInputLayout? = null
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                if (inputs.count { it.valid() } == inputs.size)
                    onValid()
            }
        }
    }
}
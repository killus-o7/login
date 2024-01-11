package com.example.login.ui

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class ValidatedTextInputLayout(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {
    var cases: List<Case> = listOf()
    private var result: Boolean = true
    fun valid(): Boolean = reset().let { cases.forEach { check(it) }}.let { result }
    private fun reset() {
        this.error = null
        result = true
    }
    private fun check (case: Case) {
        case.condition.let {
            if (!case.condition(this.editText?.text.toString()) && this.error == null)
                this.error = resources.getString(case.text)
            result = result && case.condition(this.editText?.text.toString())
        }
    }
    data class Case (
        val text: Int,
        val condition: (it: String) -> Boolean
    )
}
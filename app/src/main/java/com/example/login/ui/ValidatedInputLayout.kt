package com.example.login.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ValidatedInputLayout(context: Context, attrs: AttributeSet) : TextInputLayout(context, attrs) {
    var cases: List<Pair<Int, suspend String.() -> Boolean>>? = null
    private suspend fun check (): Boolean {
        withContext(Dispatchers.Main) {
            error = null
        }

        for ((@StringRes label, check) in cases!!){
            if (!check(editText?.text.toString())) {
                withContext(Dispatchers.Main) {
                    error = resources.getString(label)
                }
                return false
            }
        }
        return true
    }
    companion object {
        fun validate (
            inputs: List<ValidatedInputLayout>,
            onValid: () -> Unit
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                if (inputs.all { it.check() }) onValid()
            }
        }
    }
}
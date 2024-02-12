package com.example.login.data

import android.content.Context
import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.login.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@Entity
data class User (
    val name: String,
    val email: String,
    val pass: Int,
    var pfp: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    private fun replacePfp(link: String, view: ImageView) {
        pfp = link
        loadPfp(view, pfp)
    }

    fun changePfp(context: Context, view: ImageView) {
        MaterialAlertDialogBuilder(context).apply {
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
                replacePfp(et.text.toString(), view)
            }
            setNegativeButton(R.string.pfpDialogNeutral, null)
        }.show()
    }

    companion object {
        fun loadPfp(view: ImageView, pfp: String?) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.default_pfp)
                .error(R.drawable.default_pfp)
                .override(200, 200)
            Glide.with(view).load(pfp).apply(options).into(view)
        }
    }
}
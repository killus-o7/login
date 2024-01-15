package com.example.login.data

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.example.login.R
import org.jetbrains.annotations.Nullable

@Entity
data class User (
    val name: String,
    val email: String,
    val pass: Int,
    val pfp: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    fun loadImage(view: ImageView) {
        if (pfp != null)
            Glide.with(view).load("file:///android_asset/$pfp").into(view)
        else
            Glide.with(view).load(R.drawable.default_pfp).into(view)
    }
}
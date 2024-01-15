package com.example.login.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.R
import com.example.login.R.drawable.*
import com.example.login.data.User
import com.example.login.databinding.LoginItemBinding

class LoginAdapter(
    private val click: (User) -> Unit
) : RecyclerView.Adapter<LoginAdapter.ViewHolder>() {
    var data: List<User> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LoginItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position], click)
    override fun getItemCount() = data.size

    class ViewHolder(private val b: LoginItemBinding) : RecyclerView.ViewHolder(b.root) {

        @SuppressLint("CheckResult")
        fun bind(entry: User, click: (User) -> Unit) {
            b.apply {
                name.text = entry.name
                email.text = entry.email
                entry.loadImage(b.image)
                b.root.setOnClickListener { click(entry) }
            }
        }
    }
}
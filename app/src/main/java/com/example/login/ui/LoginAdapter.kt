package com.example.login.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.R
import com.example.login.R.drawable.*
import com.example.login.data.User
import com.example.login.databinding.LoginItemBinding

class LoginAdapter : RecyclerView.Adapter<LoginAdapter.ViewHolder>() {
    var data: List<User> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LoginItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])
    override fun getItemCount() = data.size

    class ViewHolder(private val b: LoginItemBinding) : RecyclerView.ViewHolder(b.root) {

        @SuppressLint("CheckResult")
        fun bind(entry: User) {
            b.apply {
                name.text = entry.name
                email.text = entry.email
                image.setImageResource(entry.pfp?.toInt() ?: default_pfp)
            }
            //Glide.with(b.image).load(entry.pfp)
        }
    }
}
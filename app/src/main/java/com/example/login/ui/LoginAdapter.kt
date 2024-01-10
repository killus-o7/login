package com.example.login.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.data.User
import com.example.login.databinding.LoginItemBinding

class LoginAdapter : RecyclerView.Adapter<LoginAdapter.ViewHolder>() {
    var data: MutableList<User> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LoginItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])
    override fun getItemCount() = data.size

    class ViewHolder(private val b: LoginItemBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(entry: User) {
            b.apply {

            }
        }
    }
}
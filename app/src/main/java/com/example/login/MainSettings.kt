package com.example.login

import android.os.Bundle
import android.view.View
import com.example.login.data.User
import com.example.login.databinding.FragmentMainSettingsBinding
import com.example.login.utils.BaseFragment

class MainSettings : BaseFragment<FragmentMainSettingsBinding>() {
    override fun getViewBinding() = FragmentMainSettingsBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.apply {
            name.text = session.username
            email.text = session.email

            logout.setOnClickListener { logout() }
        }
        User.loadPfp(b.image, session.pfp)
    }
    private fun logout() = session.logout()
}
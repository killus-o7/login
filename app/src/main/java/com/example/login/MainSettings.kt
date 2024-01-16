package com.example.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.login.databinding.FragmentMainSettingsBinding
import com.example.login.utils.BaseFragment
import com.example.login.utils.Session

class MainSettings : BaseFragment<FragmentMainSettingsBinding>() {
    override fun getViewBinding() = FragmentMainSettingsBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.apply {
            name.text = session.username
            email.text = session.email

            logout.setOnClickListener { logout() }
        }
    }
    private fun logout() = session.logout()
}
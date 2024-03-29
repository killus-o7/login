package com.example.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.login.databinding.FragmentMainNotificationsBinding
import com.example.login.databinding.FragmentMainSettingsBinding
import com.example.login.utils.BaseFragment

class MainNotifications : BaseFragment<FragmentMainNotificationsBinding>() {
    override fun getViewBinding() = FragmentMainNotificationsBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
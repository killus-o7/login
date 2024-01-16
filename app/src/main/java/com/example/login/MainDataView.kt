package com.example.login

import android.os.Bundle
import android.view.View
import com.example.login.databinding.FragmentMainDataViewBinding
import com.example.login.databinding.FragmentMainNotificationsBinding
import com.example.login.utils.BaseFragment

class MainDataView : BaseFragment<FragmentMainDataViewBinding>() {
    override fun getViewBinding() = FragmentMainDataViewBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
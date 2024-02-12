package com.example.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.login.databinding.ActivityMainBinding
import com.example.login.utils.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val b by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeFragment(MainSettings())
        b.bottomNavigation.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.nav1 -> MainSettings()
                R.id.nav2 -> MainNotifications()
                else -> MainSettings()
            }. let { changeFragment(it) }
        }
    }

    private fun changeFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content, fragment, "")
            commit()
        }
        return true
    }
}
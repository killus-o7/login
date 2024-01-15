package com.example.login

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.login.databinding.ActivityMainBinding
import com.example.login.utils.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val b by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //b.text.setOnClickListener { logout() }

        val itemView = b.bottomNavigation.menu.findItem(R.id.nav3)
        Glide.with(this)
            .asDrawable()
            .load(session.pfp!!)
            .optionalCircleCrop()
            .into(object : CustomTarget<Drawable?>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable?>?
            ) { itemView.setIcon(resource) }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })

        changeFragment(MainDataView())
        b.bottomNavigation.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.nav1 -> MainDataView()
                R.id.nav2 -> MainNotifications()
                R.id.nav3 -> MainSettings()
                else -> MainDataView()
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
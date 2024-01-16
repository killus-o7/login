package com.example.login.utils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.login.data.AppDb
abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected lateinit var b: T
    protected val db by lazy { AppDb[requireContext()] }
    protected val session by lazy { Session(requireContext()) }
    abstract fun getViewBinding(): T
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = getViewBinding()
        return b.root
    }
}
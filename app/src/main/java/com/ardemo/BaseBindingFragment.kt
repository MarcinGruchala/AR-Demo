package com.ardemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseBindingFragment<Binding : ViewDataBinding> : Fragment() {

    abstract val layoutRes: Int

    protected lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        return binding.root
    }
}

package com.ardemo.presentation

import android.os.Bundle
import com.ardemo.R
import com.ardemo.base.BaseBindingActivity
import com.ardemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override val layoutRes: Int =
        R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}

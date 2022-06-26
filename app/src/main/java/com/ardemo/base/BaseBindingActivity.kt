package com.ardemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<Binding : ViewDataBinding> : AppCompatActivity() {

  abstract val layoutRes: Int

  protected lateinit var binding: Binding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil
      .inflate(layoutInflater, layoutRes, null, false)
    setContentView(binding.root)
  }
}

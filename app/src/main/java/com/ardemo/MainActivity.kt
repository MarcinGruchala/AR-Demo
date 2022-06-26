package com.ardemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ardemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.romainguy.kotlin.math.Float3
import io.github.sceneview.ar.arcore.ArFrame
import io.github.sceneview.ar.node.ArModelNode
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override val layoutRes: Int =
        R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}

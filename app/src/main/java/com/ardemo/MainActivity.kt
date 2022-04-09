package com.ardemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import com.ardemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.colorButton.setOnClickListener {
      ViewCopier.copyViewToBitmap(
        view = binding.sceneView,
        onSuccess = { bitmap ->
          val color = bitmap.scale(1, 1).getColor(0, 0).toArgb()
          it.setBackgroundColor(color)
        },
        onError = {
          //todo
        }
      )
    }
  }
}

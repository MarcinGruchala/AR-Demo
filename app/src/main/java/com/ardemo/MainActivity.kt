package com.ardemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import com.ardemo.databinding.ActivityMainBinding
import dev.romainguy.kotlin.math.Float3
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode.INSTANT

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private lateinit var modelNode: ArModelNode

  private var isObjectPresent = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    modelNode = ArModelNode(placementMode = INSTANT)

    lifecycleScope.launchWhenStarted {
      modelNode.apply {
        loadModel(
          context = applicationContext,
          glbFileLocation = "models/FiatPunto.glb",
          autoAnimate = true,
          autoScale = true,
          centerOrigin = null
        )
        scale(0.5f)
        placementPosition = Float3(0.0f, 0.0f, -1.0f)
      }
    }

    binding.sceneView.onArFrame = { arFrame ->
      if (arFrame.session.isTrackingPlane) {
        binding.objectButton.visibility = View.VISIBLE
      }
    }

    binding.objectButton.setOnClickListener {
      if (isObjectPresent) {
        binding.sceneView.removeChild(modelNode)
        modelNode.destroy()
        binding.objectButton.text = "Place the object"
        isObjectPresent = false
      } else {
        binding.sceneView.addChild(modelNode)
        modelNode.anchor()
        binding.objectButton.text = "Remove"
        isObjectPresent = true
      }
    }

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

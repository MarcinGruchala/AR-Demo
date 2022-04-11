package com.ardemo

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import com.ardemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.romainguy.kotlin.math.Float3
import io.github.sceneview.ar.arcore.ArFrame
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode.INSTANT
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  @Inject
  lateinit var modelNode: ArModelNode

  @Inject
  lateinit var resources: ApplicationResources

  @Inject
  lateinit var viewCopier: ViewCopier

  //view model
  private var isObjectPresent = false

  //view model
  private var isPlaneDetected = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

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
      processFrame(arFrame)
    }

    binding.objectButton.setOnClickListener(::objectButtonClicked)

    binding.colorButton.setOnClickListener(::colorButtonClicked)
  }

  private fun processFrame(frame: ArFrame) {
    when (frame.updatedTrackables.isNotEmpty()) {
      true -> {
        binding.objectButton.visibility = View.VISIBLE
        isPlaneDetected = true
        binding.planeIndicator.setBackgroundColor(Color.GREEN)
      }
      false -> {
        isPlaneDetected = false
        binding.planeIndicator.setBackgroundColor(Color.RED)
      }
    }
  }

  private fun objectButtonClicked(button: View) {
    when (isObjectPresent) {
      true -> removeObject()
      false -> placeObject()
    }
  }

  private fun placeObject() {
    if (isPlaneDetected) {
      binding.sceneView.addChild(modelNode)
      modelNode.anchor()
      binding.objectButton.text = resources.removeButtonText()
      isObjectPresent = true
    } else {
      Toast
        .makeText(applicationContext, resources.noPlaneMessage(),
          Toast.LENGTH_LONG)
        .show()
    }
  }

  private fun removeObject() {
    binding.sceneView.removeChild(modelNode)
    modelNode.destroy()
    binding.objectButton.text = resources.placeObjectButton()
    isObjectPresent = false
  }

  private fun colorButtonClicked(button: View) {
    viewCopier.copyViewToBitmap(
      view = binding.sceneView,
      onSuccess = { bitmap ->
        val color = bitmap.scale(1, 1).getColor(0, 0).toArgb()
        button.setBackgroundColor(color)
      },
      onError = {
        //todo
      }
    )
  }

}

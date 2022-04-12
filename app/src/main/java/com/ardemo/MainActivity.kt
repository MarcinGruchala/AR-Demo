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

  @Inject
  lateinit var modelNode: ArModelNode

  @Inject
  lateinit var resources: ApplicationResources

  @Inject
  lateinit var utils: Utils

  private var isObjectPresent = false

  private var isPlaneDetected = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launchWhenStarted {
      modelNode.apply {
        loadModel(
          context = applicationContext,
          glbFileLocation = "models/FiatPunto.glb",
          autoAnimate = false,
          autoScale = true,
          centerOrigin = null
        )
        scale(0.25f)
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
        binding.planeIndicator.setImageResource(resources.green())
      }
      false -> {
        isPlaneDetected = false
        binding.planeIndicator.setImageResource(resources.red())
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
    when (isPlaneDetected) {
      true -> {
        binding.sceneView.addChild(modelNode)
        modelNode.anchor()
        binding.objectButton.text = resources.removeButtonText()
        isObjectPresent = true
      }
      false -> {
        Toast.makeText(
          applicationContext, resources.noPlaneMessage(), Toast.LENGTH_LONG)
          .show()
      }
    }
  }

  private fun removeObject() {
    binding.sceneView.removeChild(modelNode)
    modelNode.destroy()
    binding.objectButton.text = resources.placeObjectButton()
    isObjectPresent = false
  }

  private fun colorButtonClicked(button: View) {
    utils.copyViewToBitmap(
      view = binding.sceneView,
      onSuccess = { bitmap ->
        val color = utils.calculateAverageColor(bitmap)
        button.setBackgroundColor(color)
      },
      onError = {
        Log.e("MainActivity", "Error during screen copping")
      }
    )
  }
}

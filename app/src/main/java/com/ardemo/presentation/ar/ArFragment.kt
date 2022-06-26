package com.ardemo.presentation.ar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ardemo.R
import com.ardemo.base.BaseBindingFragment
import com.ardemo.common.ApplicationResources
import com.ardemo.databinding.FragmentArBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.romainguy.kotlin.math.Float3
import io.github.sceneview.ar.arcore.ArFrame
import io.github.sceneview.ar.node.ArModelNode
import javax.inject.Inject


@AndroidEntryPoint
class ArFragment : BaseBindingFragment<FragmentArBinding>() {

    override val layoutRes: Int = R.layout.fragment_ar

    @Inject
    lateinit var modelNode: ArModelNode

    @Inject
    lateinit var resources: ApplicationResources

    private var isObjectPresent: ObjectPlacementState = ObjectPlacementState.NOT_PLACED

    private var isPlaneDetected = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            modelNode.apply {
                loadModel(
                    context = requireContext(),
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
            ObjectPlacementState.NOT_PLACED -> placeObject()
            ObjectPlacementState.PLACED -> anchorObject()
            ObjectPlacementState.ANCHORED -> removeObject()
        }
    }

    private fun placeObject() {
        when (isPlaneDetected) {
            true -> {
                binding.sceneView.addChild(modelNode)
                binding.objectButton.text = resources.anchorObjectButton()
                isObjectPresent = ObjectPlacementState.PLACED
            }
            false -> {
                Toast.makeText(
                    requireContext(), resources.noPlaneMessage(), Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun anchorObject() {
        modelNode.anchor()
        binding.objectButton.text = resources.removeButtonText()
        isObjectPresent = ObjectPlacementState.ANCHORED
    }

    private fun removeObject() {
        binding.sceneView.removeChild(modelNode)
        modelNode.destroy()
        binding.objectButton.text = resources.placeObjectButton()
        isObjectPresent = ObjectPlacementState.NOT_PLACED
    }
}

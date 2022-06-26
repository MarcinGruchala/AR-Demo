package com.ardemo.presentation

import android.content.Context
import android.content.res.Resources
import com.ardemo.common.ApplicationResources
import com.ardemo.common.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode.INSTANT

@[Module InstallIn(ActivityComponent::class)]
object MainActivityModule {

  @Provides
  fun resources(
    @ApplicationContext context: Context
  ): Resources = context.resources

  @Provides
  fun applicationResources(
    resources: Resources
  ): ApplicationResources = ApplicationResources(resources)

  @Provides
  fun modelNode(): ArModelNode =
    ArModelNode(placementMode = INSTANT)

  @Provides
  fun viewCopier(): Utils = Utils()

}

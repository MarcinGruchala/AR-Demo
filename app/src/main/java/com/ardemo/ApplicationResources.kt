package com.ardemo

import android.content.res.Resources
import dagger.Reusable

@Reusable
class ApplicationResources constructor(
  private val resources: Resources
) {

  fun noPlaneMessage(): String =
    resources.getString(R.string.no_plane_message)

  fun placeObjectButton(): String =
    resources.getString(R.string.place_object_button)

  fun removeButtonText(): String =
    resources.getString(R.string.remove_button)

}


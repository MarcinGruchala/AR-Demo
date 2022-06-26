package com.ardemo.common

import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.ardemo.R
import dagger.Reusable

@Reusable
class ApplicationResources constructor(
    private val resources: Resources
) {

    fun noPlaneMessage(): String =
        resources.getString(R.string.no_plane_message)

    fun placeObjectButton(): String =
        resources.getString(R.string.place_object_button)

    fun anchorObjectButton(): String =
        resources.getString(R.string.anchor_object_button)

    fun removeButtonText(): String =
        resources.getString(R.string.remove_button)

    @DrawableRes
    fun addObjectIcon(): Int =
        R.drawable.ic_add

    @DrawableRes
    fun removeObjectIcon(): Int =
        R.drawable.ic_remove

    @DrawableRes
    fun anchorObjectIcon(): Int =
        R.drawable.ic_anchor

    @DrawableRes
    fun detachObjectButton(): Int =
        R.drawable.ic_detach

    @ColorRes
    fun green(): Int =
        R.color.green

    @ColorRes
    fun red(): Int =
        R.color.red
}


package com.ardemo

import android.graphics.Bitmap
import android.os.Handler
import android.os.HandlerThread
import android.view.PixelCopy
import android.view.SurfaceView
import android.view.View

object ViewCopier {

  fun copyViewToBitmap(view: View, onSuccess: (bitmap: Bitmap) -> Unit, onError: () -> Unit) {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

    val handlerThread = HandlerThread("Helper")
    handlerThread.start()

    PixelCopy.request(
      view as SurfaceView,
      bitmap,
      { copyResult ->
        if (copyResult == PixelCopy.SUCCESS) {
          onSuccess(bitmap)
        } else {
          onError()
        }
        handlerThread.quitSafely()
      }, Handler(handlerThread.looper)
    )
  }
}

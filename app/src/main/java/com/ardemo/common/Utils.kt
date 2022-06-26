package com.ardemo.common

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.os.HandlerThread
import android.view.PixelCopy
import android.view.SurfaceView
import android.view.View
import javax.inject.Singleton

@Singleton
class Utils {

  fun copyViewToBitmap(view: View, onSuccess: (bitmap: Bitmap) -> Unit, onError: () -> Unit) {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

    val handlerThread = HandlerThread("helperThread")
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

  fun calculateAverageColor(bitmap: Bitmap): Int {
    var red = 0
    var green = 0
    var blue = 0
    val height = bitmap.height
    val width = bitmap.width
    var pixelNumber = 0
    val pixels = IntArray(width * height)
    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
    var i = 0
    while (i < pixels.size) {
      val color = pixels[i]
      red += Color.red(color)
      green += Color.green(color)
      blue += Color.blue(color)
      pixelNumber++
      i ++
    }
    return Color.rgb(red / pixelNumber, green / pixelNumber, blue / pixelNumber)
  }
}

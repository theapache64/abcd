package com.theapache64.abcd.utils

import android.graphics.*
import java.io.File

object BitmapUtils {

    fun join(mapFile: File, shareFile: File, text: String?): Bitmap {

        // Decoding file to bitmap
        val bmp1 = BitmapFactory.decodeFile(mapFile.absolutePath)
        val bmp2 = BitmapFactory.decodeFile(shareFile.absolutePath)


        return join(bmp1, bmp2, text)
    }

    private fun join(bmp1: Bitmap, bmp2: Bitmap, text: String?): Bitmap {

        // Calc dimension
        val jbWidth = bmp1.width + bmp2.width
        val jbHeight = bmp1.height

        // Creating new blank bitmap
        val joinedBitmap = Bitmap.createBitmap(jbWidth, jbHeight, Bitmap.Config.ARGB_8888)
        val jbCanvas = Canvas(joinedBitmap)

        // Drawing
        jbCanvas.drawBitmap(bmp1, 0f, 0f, null)
        jbCanvas.drawBitmap(bmp2, bmp1.width.toFloat(), 0f, null)

        if (text != null) {
            val paint = Paint()
            paint.textSize = 15f
            paint.isAntiAlias = true
            paint.color = Color.BLACK
            jbCanvas.drawText(text, 10f, 20f, paint)
        }

        return joinedBitmap
    }
}
package com.theapache64.abcd.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    fun saveBitmap(context: Context, fileName: String, bitmap: Bitmap): File {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$fileName.png")
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        return file
    }

}
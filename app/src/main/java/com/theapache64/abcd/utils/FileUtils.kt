package com.theapache64.abcd.utils

import android.graphics.Bitmap
import android.os.Environment
import com.theapache64.twinkill.logger.info
import java.io.File
import java.io.FileOutputStream

object FileUtils {

    fun saveBitmap(fileName: String, bitmap: Bitmap): File {
        val extStorage = Environment.getExternalStorageDirectory().absolutePath
        val file = File("$extStorage/abcd/$fileName.png")
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        return file
    }
}
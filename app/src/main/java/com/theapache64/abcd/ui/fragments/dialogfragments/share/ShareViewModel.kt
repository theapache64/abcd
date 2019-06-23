package com.theapache64.abcd.ui.fragments.dialogfragments.share

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.theapache64.abcd.utils.BitmapUtils
import com.theapache64.abcd.utils.FileUtils
import java.io.File
import javax.inject.Inject

class ShareViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private lateinit var outputFile: File
    private lateinit var mapFile: File

    private val shareFile = MutableLiveData<File>()
    fun getShareFile(): LiveData<File> = shareFile


    fun init(mapFile: File, outputFile: File) {
        this.mapFile = mapFile
        this.outputFile = outputFile
    }

    fun shareSegMap() {
        this.shareFile.value = mapFile
    }

    fun shareOutputFile() {
        this.shareFile.value = outputFile
    }

    fun shareMapAndOutput() {
        val joinedBitmap = BitmapUtils.join(mapFile, outputFile, "GauGAN + abcd")
        val bitmapFile = FileUtils.saveBitmap(
            getApplication(),
            "map_and_output_${mapFile.nameWithoutExtension}",
            joinedBitmap
        )
        this.shareFile.value = bitmapFile
    }

}
package com.theapache64.abcd.ui.activities.result

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.repositories.GauganRepository
import com.theapache64.abcd.models.Style
import com.theapache64.twinkill.network.utils.Resource
import java.io.File
import javax.inject.Inject

class ResultViewModel @Inject constructor(
    private val gauganRepository: GauganRepository
) : ViewModel() {

    private lateinit var artisticStyle: Style
    private lateinit var style: Style
    private lateinit var mapFile: File

    fun init(mapFile: File, style: Style, artisticStyle: Style) {
        this.mapFile = mapFile
        this.style = style
        this.artisticStyle = artisticStyle
    }

    fun getGauganOutput(): LiveData<Resource<Bitmap>> = gauganRepository.receiveImage(mapFile, style, artisticStyle)


}
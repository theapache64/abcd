package com.theapache64.abcd.ui.activities.result

import android.graphics.Bitmap
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.receiveimage.ReceiveImageRequest
import com.theapache64.abcd.data.repositories.GauganRepository
import com.theapache64.abcd.models.Style
import com.theapache64.twinkill.network.utils.Resource
import java.io.File
import javax.inject.Inject

class ResultViewModel @Inject constructor(
    private val gauganRepository: GauganRepository
) : ViewModel() {

    lateinit var mapFile: File
    var outputFile: File? = null
    lateinit var style: Style
    lateinit var inputUri: String
    val isInputVisible = ObservableBoolean(false)
    private val receiveImageRequest = MutableLiveData<ReceiveImageRequest>()

    fun getGauganOutput(): LiveData<Resource<Bitmap>> = Transformations.switchMap(receiveImageRequest) { request ->
        gauganRepository.receiveImage(request)
    }

    fun loadOutput(request: ReceiveImageRequest) {
        this.receiveImageRequest.value = request
    }

    fun setInputVisible(boolean: Boolean) {
        this.isInputVisible.set(boolean)
    }

    fun init(inputFile: File, style: Style) {
        this.mapFile = inputFile
        this.inputUri = "file://${inputFile.absolutePath}"
        this.style = style
    }


}
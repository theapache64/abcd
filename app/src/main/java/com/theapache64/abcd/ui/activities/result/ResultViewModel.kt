package com.theapache64.abcd.ui.activities.result

import android.graphics.Bitmap
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.receiveimage.ReceiveImageRequest
import com.theapache64.abcd.data.remote.updaterandom.UpdateRandomRequest
import com.theapache64.abcd.data.repositories.GauganRepository
import com.theapache64.abcd.data.repositories.StyleRepository
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

    private val updateRandomRequest = MutableLiveData<UpdateRandomRequest>()
    private val updateRandomResponse = Transformations.switchMap(updateRandomRequest) {
        gauganRepository.updateRandom(it)
    }

    private val receiveImageRequest = MutableLiveData<ReceiveImageRequest>()

    fun getGauganOutput(): LiveData<Resource<Bitmap>> = Transformations.switchMap(receiveImageRequest) { request ->
        gauganRepository.receiveImage(request)
    }

    fun load(request: ReceiveImageRequest) {
        if (style.code == StyleRepository.CODE_RANDOM) {
            // random
            this.updateRandomRequest.value = UpdateRandomRequest(request.mapFile.nameWithoutExtension)
        } else {
            loadDirectOutput(request)
        }
    }

    fun loadDirectOutput(request: ReceiveImageRequest) {
        // direct output
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

    fun getUpdateRandomResponse() = updateRandomResponse


}
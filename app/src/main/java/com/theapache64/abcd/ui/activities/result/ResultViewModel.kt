package com.theapache64.abcd.ui.activities.result

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.receiveimage.ReceiveImageRequest
import com.theapache64.abcd.data.remote.submitmap.SubmitMapRequest
import com.theapache64.abcd.data.remote.updaterandom.UpdateRandomRequest
import com.theapache64.abcd.data.repositories.ApiRepository
import com.theapache64.abcd.data.repositories.StyleRepository
import com.theapache64.abcd.models.Style
import com.theapache64.twinkill.network.utils.Resource
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class ResultViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    var isErrorOnGen: Boolean = false
    lateinit var mapFile: File
    var outputFile: File? = null
    lateinit var style: Style
    lateinit var inputUri: String
    val isInputVisible = ObservableBoolean(false)

    private val updateRandomRequest = MutableLiveData<UpdateRandomRequest>()
    private val updateRandomResponse = Transformations.switchMap(updateRandomRequest) {
        apiRepository.updateRandom(it)
    }

    private val receiveImageRequest = MutableLiveData<ReceiveImageRequest>()

    fun getFinalImageOutput(): LiveData<Resource<Bitmap>> = Transformations.switchMap(receiveImageRequest) { request ->
        apiRepository.receiveImage(request)
    }

    fun load(request: ReceiveImageRequest) {
        if (isErrorOnGen) {

            val bmp = BitmapFactory.decodeFile(mapFile.absolutePath)
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            val base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT)
            bmp.recycle()

            // need to submit map
            this.submitMapRequest.value = SubmitMapRequest(
                base64Image,
                mapFile.nameWithoutExtension
            )

        } else {
            if (style.apiCode == StyleRepository.CODE_RANDOM) {
                // random
                this.updateRandomRequest.value = UpdateRandomRequest(request.mapFile.nameWithoutExtension)
            } else {
                loadDirectOutput(request)
            }
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

    private val submitMapRequest = MutableLiveData<SubmitMapRequest>()

    private val submitMapResponse = Transformations.switchMap(submitMapRequest) {
        apiRepository.submitMap(it)
    }


    fun getSubmitMapResponse() = submitMapResponse


}
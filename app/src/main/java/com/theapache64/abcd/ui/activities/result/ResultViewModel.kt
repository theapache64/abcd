package com.theapache64.abcd.ui.activities.result

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.receiveimage.ReceiveImageRequest
import com.theapache64.abcd.data.repositories.GauganRepository
import com.theapache64.twinkill.network.utils.Resource
import javax.inject.Inject

class ResultViewModel @Inject constructor(
    private val gauganRepository: GauganRepository
) : ViewModel() {

    private val receiveImageRequest = MutableLiveData<ReceiveImageRequest>()

    fun getGauganOutput(): LiveData<Resource<Bitmap>> = Transformations.switchMap(receiveImageRequest) { request ->
        gauganRepository.receiveImage(request)
    }

    fun loadOutput(request: ReceiveImageRequest) {
        this.receiveImageRequest.value = request
    }

}
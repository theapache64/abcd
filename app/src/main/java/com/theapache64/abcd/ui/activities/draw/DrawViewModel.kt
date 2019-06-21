package com.theapache64.abcd.ui.activities.draw


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.submitmap.SubmitMapRequest
import com.theapache64.abcd.data.repositories.BrushRepository
import com.theapache64.abcd.data.repositories.GauganRepository
import com.theapache64.abcd.models.Brush
import javax.inject.Inject

class DrawViewModel @Inject constructor(
    private val brushesRepository: BrushRepository,
    private val gauganRepository: GauganRepository
) : ViewModel() {

    private val submitMapRequest = MutableLiveData<SubmitMapRequest>()
    private val submitMapResponse = Transformations.switchMap(submitMapRequest) {
        gauganRepository.submitMap(it)
    }

    fun getSubmitMapResponse() = submitMapResponse
    fun submitMap(name: String, base64Image: String) {
        submitMapRequest.value = SubmitMapRequest(base64Image, name)
    }

    private val seaAndSky = MutableLiveData(brushesRepository.getSeaAndSky())
    fun getSeaAndSky(): LiveData<Pair<Brush, Brush>> = seaAndSky
}
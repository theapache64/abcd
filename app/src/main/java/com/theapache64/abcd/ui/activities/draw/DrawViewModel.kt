package com.theapache64.abcd.ui.activities.draw


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.submitmap.SubmitMapRequest
import com.theapache64.abcd.data.repositories.ApiRepository
import com.theapache64.abcd.data.repositories.BrushRepository
import com.theapache64.abcd.models.Brush
import javax.inject.Inject

class DrawViewModel @Inject constructor(
    private val brushesRepository: BrushRepository,
    private val apiRepository: ApiRepository
) : ViewModel() {

    lateinit var mountain: Brush
    lateinit var sky: Brush
    lateinit var sea: Brush

    lateinit var submittedMapName: String
    private val submitMapRequest = MutableLiveData<SubmitMapRequest>()

    private val submitMapResponse = Transformations.switchMap(submitMapRequest) {
        apiRepository.submitMap(it)
    }

    fun getSubmitMapResponse() = submitMapResponse
    fun submitMap(name: String, base64Image: String) {
        this.submittedMapName = name
        submitMapRequest.value = SubmitMapRequest(base64Image, name)
    }

    private val seaSkyAndMountain = MutableLiveData(brushesRepository.getSeaSkyAndMountain())
    fun getSkySeaAndMountain(): LiveData<Triple<Brush, Brush, Brush>> = seaSkyAndMountain
}
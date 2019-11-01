package com.theapache64.abcd.ui.activities.draw


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.theapache64.abcd.data.remote.submitmap.SubmitMapRequest
import com.theapache64.abcd.data.repositories.ApiRepository
import com.theapache64.abcd.data.repositories.BrushRepository
import com.theapache64.abcd.data.repositories.SharedPrefRepository
import com.theapache64.abcd.models.Brush
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent
import javax.inject.Inject

class DrawViewModel @Inject constructor(
    private val brushesRepository: BrushRepository,
    private val apiRepository: ApiRepository,
    private val prefRepository: SharedPrefRepository
) : ViewModel() {

    companion object {
        const val DONATION_USAGE_THRESHOLD = 5
    }

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

    private val donation =
        SingleLiveEvent<Boolean>()

    init {
        this.donation.value = isEligibleForDonation(
            prefRepository.getUsageCount(),
            prefRepository.isInitAskDone(),
            prefRepository.isThresholdAskDone()
        )
    }

    private fun isEligibleForDonation(
        usageCount: Int,
        isInitAskDone: Boolean,
        isThresholdAskDone: Boolean
    ): Boolean {
        return if (isInitAskDone && isThresholdAskDone) {
            false
        } else {
            if (usageCount == 0 && !isInitAskDone) {
                prefRepository.setInitAskDone()
                return true
            }

            if (usageCount >= DONATION_USAGE_THRESHOLD && !isThresholdAskDone) {
                prefRepository.setThresholdAskDone()
                return true
            }

            return false
        }
    }

    fun getDonation(): LiveData<Boolean> = donation
}
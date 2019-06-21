package com.theapache64.abcd.data.repositories

import com.theapache64.abcd.data.remote.ApiInterface
import com.theapache64.abcd.data.remote.submitmap.SubmitMapRequest
import javax.inject.Inject

class GauganRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    fun submitMap(submitMapRequest: SubmitMapRequest) = apiInterface.submitMap(
        "data:image/png;base64,${submitMapRequest.imageBase64}",
        submitMapRequest.name
    )
}
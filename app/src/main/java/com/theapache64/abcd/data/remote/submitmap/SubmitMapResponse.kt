package com.theapache64.abcd.data.remote.submitmap

import com.squareup.moshi.Json
import com.theapache64.twinkill.network.data.remote.base.BaseApiResponse

class SubmitMapResponse(error: Boolean, message: String, data: Data?) :
    BaseApiResponse<SubmitMapResponse.Data>(error, message, data) {
    class Data(
        @Json(name = "name")
        val name: String
    )
}
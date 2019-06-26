package com.theapache64.abcd.data.remote.receiveimage

import com.squareup.moshi.Json
import com.theapache64.twinkill.network.data.remote.base.BaseApiResponse

class ReceiveImageResponse(error: Boolean, message: String, data: Data?) :
    BaseApiResponse<ReceiveImageResponse.Data>(error, message, data) {
    class Data(
        @Json(name = "name")
        val name: String
    )
}
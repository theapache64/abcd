package com.theapache64.abcd.data.remote.updaterandom

import com.squareup.moshi.Json
import com.theapache64.twinkill.network.data.remote.base.BaseApiResponse

class UpdateRandomResponse(error: Boolean, message: String, data: Data?) :
    BaseApiResponse<UpdateRandomResponse.Data>(error, message, data) {
    class Data(
        @Json(name = "name")
        val name: String
    )
}
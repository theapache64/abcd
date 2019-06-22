package com.theapache64.abcd.data.remote.updaterandom

import com.squareup.moshi.Json

class UpdateRandomResponse(
    @Json(name = "success")
    val isSuccess: Boolean
)
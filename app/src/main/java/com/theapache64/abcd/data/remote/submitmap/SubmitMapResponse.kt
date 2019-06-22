package com.theapache64.abcd.data.remote.submitmap

import com.squareup.moshi.Json

class SubmitMapResponse(
    @Json(name = "success")
    val isSuccess: Boolean
)
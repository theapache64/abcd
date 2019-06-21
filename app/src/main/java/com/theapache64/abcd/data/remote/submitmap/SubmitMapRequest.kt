package com.theapache64.abcd.data.remote.submitmap

import com.squareup.moshi.Json

class SubmitMapRequest(
    @Json(name = "imageBase64")
    val imageBase64: String,

    @Json(name = "name")
    val name: String
)
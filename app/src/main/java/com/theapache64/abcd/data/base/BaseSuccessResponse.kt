package com.theapache64.abcd.data.base

import com.squareup.moshi.Json

open class BaseSuccessResponse(
    @Json(name = "success")
    val isSuccess: Boolean
)
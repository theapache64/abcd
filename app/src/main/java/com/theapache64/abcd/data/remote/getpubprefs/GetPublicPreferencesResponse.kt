package com.theapache64.abcd.data.remote.getpubprefs

import com.squareup.moshi.Json
import com.theapache64.twinkill.network.data.remote.base.BaseApiResponse

class GetPublicPreferencesResponse(error: Boolean, message: String, data: Data?) :
    BaseApiResponse<GetPublicPreferencesResponse.Data>(error, message, data) {

    class Data(
        @Json(name = "prefs")
        val prefs: Prefs
    )

    class Prefs(
        @Json(name = "down_reason")
        val downReason: String, // We are under maintenance
        @Json(name = "is_down")
        val isDown: Boolean, // false
        @Json(name = "latest_version_code")
        val latestVersionCode: Int, // 5
        @Json(name = "latest_version_message")
        val latestVersionMessage: String // New version available. Please update
    )
}

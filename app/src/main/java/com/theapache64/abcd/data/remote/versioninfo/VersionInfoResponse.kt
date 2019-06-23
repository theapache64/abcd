package com.theapache64.abcd.data.remote.versioninfo

import com.squareup.moshi.Json

class VersionInfoResponse(
    @Json(name = "latest_version_code")
    val latestVersionCode : Int,

    @Json(name = "version_expiry_message")
    val versionExpiryMessage : String
)
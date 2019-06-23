package com.theapache64.abcd.data.remote

import androidx.lifecycle.LiveData
import com.theapache64.abcd.data.remote.versioninfo.VersionInfoResponse
import com.theapache64.twinkill.network.utils.Resource
import retrofit2.http.GET

interface SheetyApiInterface {

    @GET("d744ed5f-aadf-4207-a9a6-9141194c9fc7")
    fun getVersionInfo(): LiveData<Resource<List<VersionInfoResponse>>>

}